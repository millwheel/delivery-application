package msa.restaurant.service;

import msa.restaurant.DAO.Store;
import msa.restaurant.DTO.StoreForm;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ConvertMessageService {
    public String createMessageForStoreInfo(Store store){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("storeId", store.getStoreId());
        data.put("name", store.getName());
        data.put("foodKind", store.getFoodKind());
        data.put("phoneNumber", store.getPhoneNumber());
        data.put("address", store.getAddress());
        data.put("addressDetail", store.getAddressDetail());
        data.put("location", store.getLocation().toString());
        data.put("introduction", store.getIntroduction());
        data.put("menuList", store.getMenuList());
        jsonObject.put("dataType", "store");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

}
