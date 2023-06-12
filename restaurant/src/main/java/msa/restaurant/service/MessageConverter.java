package msa.restaurant.service;

import msa.restaurant.dto.StoreSqsDto;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class MessageConverter {
    public String createMessageForStoreInfo(StoreSqsDto storeSqsDto){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject(storeSqsDto);
        jsonObject.put("dataType", "store");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

}
