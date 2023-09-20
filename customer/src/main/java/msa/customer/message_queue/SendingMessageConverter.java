package msa.customer.message_queue;

import msa.customer.entity.order.Order;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendingMessageConverter {

    public String createMessageToCreateOrder(Order order){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject(order);
        jsonObject.put("dataType", "order");
        jsonObject.put("method", "create");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
}
