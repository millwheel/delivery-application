package msa.restaurant.service;

import msa.restaurant.DAO.Store;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class StoreJsonService {
    public String createMessageForStoreInfo(Store store){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("restaurantId", store.getStoreId());
        jsonObject.put("name", store.getName());
        jsonObject.put("foodKind", store.getFoodKind());
        jsonObject.put("phoneNumber", store.getPhoneNumber());
        jsonObject.put("address", store.getAddress());
        jsonObject.put("addressDetail", store.getAddressDetail());
        jsonObject.put("location", store.getLocation());
        jsonObject.put("introduction", store.getIntroduction());
        return jsonObject.toString();
    }
}
