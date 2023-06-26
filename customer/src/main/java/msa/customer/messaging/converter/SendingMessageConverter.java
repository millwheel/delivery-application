package msa.customer.converter;

import msa.customer.entity.order.Order;
import org.json.JSONObject;

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
