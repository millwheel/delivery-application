package msa.rider.service.messaging;

import msa.rider.dto.rider.RiderPartDto;
import msa.rider.entity.order.OrderStatus;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendingMessageConverter {

    public String createMessageToChangeOrderStatus(String orderId, OrderStatus orderStatus){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "menu");
        jsonObject.put("method", "change");
        data.put("orderId", orderId);
        data.put("orderStatus", orderStatus);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToAssignRider(String orderId, RiderPartDto riderPartDto, OrderStatus orderStatus){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject riderData = new JSONObject(riderPartDto);
        jsonObject.put("dataType", "menu");
        jsonObject.put("method", "change");
        data.put("orderId", orderId);
        data.put("orderStatus", orderStatus);
        data.put("riderData", riderData);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
}
