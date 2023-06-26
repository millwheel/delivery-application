package msa.restaurant.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

public class ReceivingMessageConverter {
    public void processMessage(String message) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(message);
    }
}
