package msa.restaurant.service.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import msa.restaurant.deserializer.OrderDeserializer;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.service.order.OrderService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ReceivingMessageConverter {

    private final OrderService orderService;

    public ReceivingMessageConverter(OrderService orderService) {
        this.orderService = orderService;
    }

    public void processMessage(String message) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(message);
        if (jsonObject.get("dataType").equals("order")){
            processOrderData(jsonObject);
        }
    }

    public void processOrderData(JSONObject jsonObject) throws JsonProcessingException {
        if (jsonObject.get("method").equals("create")){
            Order order = convertOrderData(jsonObject);
            orderService.createOrder(order);
        } else if (jsonObject.get("method").equals("change")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = (String) data.get("orderId");
            OrderStatus orderStatus = (OrderStatus) data.get("orderStatus");
            orderService.updateOrderStatusFromOtherServer(orderId, orderStatus);
        }
    }

    public Order convertOrderData(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Order.class, new OrderDeserializer());
        objectMapper.registerModule(module);
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, Order.class);
    }
}
