package msa.customer.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.StoreSqsDto;
import msa.customer.service.StoreService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConverter {

    private final StoreService storeService;

    public MessageConverter(StoreService storeService) {
        this.storeService = storeService;
    }

    public void processMessage(String message) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(message);
        if (jsonObject.get("dataType").equals("store")){
            if (jsonObject.get("method").equals("create")){
                StoreSqsDto storeSqsDto = convertStoreData(jsonObject);
                storeService.createStore(storeSqsDto);
            } else if (jsonObject.get("method").equals("update")) {
                StoreSqsDto storeSqsDto = convertStoreData(jsonObject);
                storeService.updateStore(storeSqsDto);
            } else if (jsonObject.get("method").equals("delete")) {
                String storeId = (String) jsonObject.get("storeId");
                storeService.deleteStore(storeId);
            }
        }
    }

    public StoreSqsDto convertStoreData(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(StoreSqsDto.class, new StoreDeserializer());
        objectMapper.registerModule(module);
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, StoreSqsDto.class);
    }

}
