package msa.rider.sqs;

import msa.rider.dto.rider.RiderPartDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendingMessageConverter {

    public String createMessageToAssignRider(Order order, RiderPartDto riderPartDto, OrderStatus orderStatus){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject riderData = new JSONObject(riderPartDto);
        jsonObject.put("dataType", "order");
        jsonObject.put("method", "assign");
        data.put("customerId", order.getCustomerId());
        data.put("storeId", order.getStoreId());
        data.put("orderId", order.getOrderId());
        data.put("orderStatus", orderStatus);
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
