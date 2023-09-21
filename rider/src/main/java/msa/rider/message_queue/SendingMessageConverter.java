package msa.rider.message_queue;

import msa.rider.dto.rider.RiderSqsDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendingMessageConverter {

    public String createMessageToAssignRider(Order order, RiderSqsDto riderData){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "order");
        jsonObject.put("method", "assign");
        data.put("customerId", order.getCustomerId());
        data.put("storeId", order.getStoreId());
        data.put("orderId", order.getOrderId());
        data.put("orderStatus", order.getOrderStatus());
        data.put("riderData", riderData);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToChangeOrderStatus(Order order, OrderStatus orderStatus){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "order");
        jsonObject.put("method", "change");
        data.put("customerId", order.getCustomerId());
        data.put("storeId", order.getStoreId());
        data.put("orderId", order.getOrderId());
        data.put("orderStatus", orderStatus);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
}
