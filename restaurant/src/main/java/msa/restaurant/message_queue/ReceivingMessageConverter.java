package msa.restaurant.message_queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.restaurant.deserializer.PointMixin;
import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.service.order.OrderService;
import msa.restaurant.sse.SseService;
import org.json.JSONObject;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
public class ReceivingMessageConverter {

    private final OrderService orderService;
    private final SseService sseService;

    public ReceivingMessageConverter(OrderService orderService, SseService sseService) {
        this.orderService = orderService;
        this.sseService = sseService;
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
            sseService.updateOrderFromSqs(storeId, order.getOrderId());
        } else if (jsonObject.get("method").equals("assign")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = data.getString("orderId");
            String storeId = data.getString("storeId");
            OrderStatus orderStatus = data.getEnum(OrderStatus.class, "orderStatus");
            RiderPartDto riderPartDto= new ObjectMapper().readValue(data.get("riderData").toString(), RiderPartDto.class);
            orderService.assignRiderToOrder(orderId, orderStatus, riderPartDto);
            sseService.updateOrderFromSqs(storeId, orderId);
        } else if (jsonObject.get("method").equals("change")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = data.getString("orderId");
            String storeId = data.getString("storeId");
            OrderStatus orderStatus = data.getEnum(OrderStatus.class,"orderStatus");
            orderService.changeOrderStatusFromOtherServer(orderId, orderStatus);
            sseService.updateOrderFromSqs(storeId, orderId);
        }
    }

    public Order convertOrderDataWithCustomDeserializer(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Point.class, PointMixin.class);
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, Order.class);
    }
}
