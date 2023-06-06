package msa.restaurant.service;

import msa.restaurant.DAO.Store;
import msa.restaurant.DTO.StoreForm;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ConvertMessageService {
    public String createMessageForStoreInfo(Store store){
        StoreForm data = createStoreForm(store);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("dataType", "store");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public StoreForm createStoreForm(Store store){
        StoreForm data = new StoreForm();
        data.setStoreId(store.getStoreId());
        data.setName(store.getName());
        data.setFoodKind(store.getFoodKind());
        data.setPhoneNumber(store.getPhoneNumber());
        data.setAddress(store.getAddress());
        data.setAddressDetail(store.getAddressDetail());
        data.setLocation(store.getLocation());
        data.setIntroduction(store.getIntroduction());
        data.setMenuList(store.getMenuList());
        return data;
    }
}
