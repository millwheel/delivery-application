package msa.customer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.customer.dto.StoreSqsDto;
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
            StoreSqsDto storeSqsDto = objectMapper.readValue(data, StoreSqsDto.class);
            System.out.println(storeSqsDto);
            storeService.updateStoreInfo(storeSqsDto);
        }
    }
}
