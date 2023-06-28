package msa.rider.service.messaging;

import msa.rider.entity.order.OrderStatus;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendingMessageConverter {

    public String createMessageToUpdateOrderStatus(OrderStatus orderStatus){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "menu");
        jsonObject.put("method", "update");
        data.put("orderStatus", orderStatus);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
}
