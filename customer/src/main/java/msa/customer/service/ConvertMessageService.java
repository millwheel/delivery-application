package msa.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.customer.DAO.Store;
import msa.customer.DTO.StoreForm;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ConvertMessageService {

    private final StoreService storeService;

    public ConvertMessageService(StoreService storeService) {
        this.storeService = storeService;
    }

    public void processMessage(String message) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(message);
        ObjectMapper objectMapper = new ObjectMapper();
        if (jsonObject.get("dataType").equals("store")){
            String data = jsonObject.get("data").toString();
            StoreForm storeForm = objectMapper.readValue(data, StoreForm.class);
            System.out.println(storeForm);
            storeService.updateStoreInfo(storeForm);
        }
    }
}
