package msa.restaurant.sqs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import msa.restaurant.deserializer.OrderDeserializer;
import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.service.order.OrderService;
import msa.restaurant.sse.OrderInfoSseService;
import msa.restaurant.sse.OrderListSseService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ReceivingMessageConverter {

    private final OrderService orderService;
    private final OrderListSseService orderListSseService;
    private final OrderInfoSseService orderInfoSseService;

    public ReceivingMessageConverter(OrderService orderService, OrderListSseService orderListSseService, OrderInfoSseService orderInfoSseService) {
        this.orderService = orderService;
        this.orderListSseService = orderListSseService;
        this.orderInfoSseService = orderInfoSseService;
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
            Order order = convertOrderDataWithCustomDeserializer(data);
            String storeId = order.getStoreId();
            orderService.createOrder(order);
            orderListSseService.updateOrderListFromSqs(storeId);
            orderInfoSseService.updateOrderInfoFromSqs(storeId, order.getOrderId());
        } else if (jsonObject.get("method").equals("assign")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = data.getString("orderId");
            String storeId = data.getString("storeId");
            OrderStatus orderStatus = data.getEnum(OrderStatus.class, "orderStatus");
            RiderPartDto riderPartDto= new ObjectMapper().readValue(data.get("riderData").toString(), RiderPartDto.class);
            orderService.assignRiderToOrder(orderId, orderStatus, riderPartDto);
            orderListSseService.updateOrderListFromSqs(storeId);
            orderInfoSseService.updateOrderInfoFromSqs(storeId, orderId);
        } else if (jsonObject.get("method").equals("change")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = data.getString("orderId");
            String storeId = data.getString("storeId");
            OrderStatus orderStatus = (OrderStatus) data.get("orderStatus");
            orderService.changeOrderStatusFromOtherServer(orderId, orderStatus);
            orderListSseService.updateOrderListFromSqs(storeId);
            orderInfoSseService.updateOrderInfoFromSqs(storeId, orderId);
        }
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
