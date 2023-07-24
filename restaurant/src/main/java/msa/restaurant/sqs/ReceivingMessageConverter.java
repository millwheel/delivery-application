package msa.restaurant.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import msa.restaurant.deserializer.OrderDeserializer;
import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderMenu;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.service.order.OrderService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceivingMessageConverter {

    private final OrderService orderService;

    public ReceivingMessageConverter(OrderService orderService) {
        this.orderService = orderService;
    }

    public void processMessage(String message) throws JsonProcessingException {
        JSONObject messageJsonObject = new JSONObject(message);
        if (messageJsonObject.get("dataType").equals("order")){
            processOrderData(messageJsonObject);
        }
    }

    public void processOrderData(JSONObject jsonObject) throws JsonProcessingException {
        if (jsonObject.get("method").equals("create")){
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            Order order = convertOrderDataIndividual(data);
            orderService.createOrder(order);
        } else if (jsonObject.get("method").equals("assign")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = (String) data.get("orderId");
            OrderStatus orderStatus = data.getEnum(OrderStatus.class, "orderStatus");
            RiderPartDto riderPartDto= new ObjectMapper().readValue(data.get("riderData").toString(), RiderPartDto.class);
            orderService.assignRiderToOrder(orderId, orderStatus, riderPartDto);
        } else if (jsonObject.get("method").equals("change")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = (String) data.get("orderId");
            OrderStatus orderStatus = (OrderStatus) data.get("orderStatus");
            orderService.changeOrderStatusFromOtherServer(orderId, orderStatus);
        }
    }

    public Order convertOrderDataIndividual(JSONObject data) throws JsonProcessingException {
        String orderId = data.getString("orderId");
        String orderTime = data.getString("orderTime");
        OrderStatus orderStatus = data.getEnum(OrderStatus.class, "orderStatus");
        String customerId = data.getString("customerId");
        String customerName = data.getString("customerName");
        String customerPhoneNumber = data.getString("customerPhoneNumber");
        String customerAddress = data.getString("customerAddress");
        String customerAddressDetail = data.getString("customerAddressDetail");
        Point customerLocation = new ObjectMapper().readValue(data.get("customerLocation").toString(), Point.class);
        JSONArray orderMenuJsonArray = data.getJSONArray("menuInBasketList");
        List<OrderMenu> orderMenuList = new ArrayList<>();
        for (int i = 0; i < orderMenuJsonArray.length(); i++){
            JSONObject orderMenuJson = orderMenuJsonArray.getJSONObject(i);
            OrderMenu orderMenu = new OrderMenu();
            orderMenu.setMenuId(orderMenuJson.getString("menuId"));
            orderMenu.setMenuName(orderMenuJson.getString("menuName"));
            orderMenu.setCount(orderMenuJson.getInt("count"));
            orderMenu.setEachPrice(orderMenuJson.getInt("eachPrice"));
            orderMenu.setPrice(orderMenuJson.getInt("price"));
        }
        int totalPrice = data.getInt("totalPrice");
        String storeId = data.getString("storeId");
        String storeName = data.getString("storeName");
        String storePhoneNumber = data.getString("storePhoneNumber");
        String storeAddress = data.getString("storeAddress");
        String storeAddressDetail = data.getString("storeAddressDetail");
        Point storeLocation = new ObjectMapper().readValue(data.get("storeLocation").toString(), Point.class);
        return new Order(orderId, orderTime, orderStatus, customerId, customerName, customerPhoneNumber,
                customerAddress, customerAddressDetail, customerLocation, orderMenuList, totalPrice,
                storeId, storeName, storePhoneNumber, storeAddress, storeAddressDetail, storeLocation);
    }

    public Order convertOrderDataWithCustomDeserializer(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Order.class, new OrderDeserializer());
        objectMapper.registerModule(module);
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, Order.class);
    }
}
