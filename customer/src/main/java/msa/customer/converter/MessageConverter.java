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
            createStoreFromMessage(jsonObject);
        }
    }

    public void createStoreFromMessage(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(StoreSqsDto.class, new StoreDeserializer());
        objectMapper.registerModule(module);
        String data = jsonObject.get("data").toString();
        StoreSqsDto storeSqsDto = objectMapper.readValue(data, StoreSqsDto.class);
        log.info("Update store info={}", storeSqsDto.getStoreId());
        storeService.updateStoreInfo(storeSqsDto);
    }
}
