package msa.restaurant.converter;

import msa.restaurant.dto.store.StoreSqsDto;
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

    public String createMessageToDeleteStore(String storeId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataType", "delete_store");
        jsonObject.put("storeId", storeId);
        return jsonObject.toString();
    }

}
