package msa.restaurant.messaging.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

public class ReceivingMessageConverter {
    public void processMessage(String message) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(message);
        if (jsonObject.get("dataType").equals("order")){
            processOrderData(jsonObject);
        }
    }

    public void processOrderData(JSONObject jsonObject){
        if (jsonObject.get("create").equals("create")){

        }
    }
}
