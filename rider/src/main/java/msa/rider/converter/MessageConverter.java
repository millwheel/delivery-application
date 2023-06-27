package msa.rider.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import msa.rider.dto.MenuSqsDto;
import msa.rider.dto.StoreSqsDto;
import msa.rider.service.MenuService;
import msa.rider.service.StoreService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConverter {

    private final StoreService storeService;
    private final MenuService menuService;

    @Autowired
    public MessageConverter(StoreService storeService, MenuService menuService) {
        this.storeService = storeService;
        this.menuService = menuService;
    }

    public void processMessage(String message) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(message);
        if (jsonObject.get("dataType").equals("store")){
            processStoreData(jsonObject);
        } else if (jsonObject.get("dataType").equals("menu")) {
            processMenuData(jsonObject);
        }
    }

    public void processStoreData(JSONObject jsonObject) throws JsonProcessingException {
        if (jsonObject.get("method").equals("create")){
            StoreSqsDto storeSqsDto = convertStoreData(jsonObject);
            storeService.createStore(storeSqsDto);
        } else if (jsonObject.get("method").equals("updateStore")) {
            StoreSqsDto storeSqsDto = convertStoreData(jsonObject);
            storeService.updateStore(storeSqsDto);
        } else if (jsonObject.get("method").equals("deleteById")) {
            String storeId = (String) jsonObject.get("storeId");
            storeService.deleteStore(storeId);
        }
    }

    public void processMenuData(JSONObject jsonObject) throws JsonProcessingException {
        if (jsonObject.get("method").equals("create")){
            MenuSqsDto menuSqsDto = convertMenuData(jsonObject);
            menuService.createMenu(menuSqsDto);
        } else if (jsonObject.get("method").equals("updateStore")) {
            MenuSqsDto menuSqsDto = convertMenuData(jsonObject);
            menuService.updateMenu(menuSqsDto);
        } else if (jsonObject.get("method").equals("deleteById")){
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String menuId = (String) data.get("menuId");
            menuService.deleteMenu(menuId);
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

    public MenuSqsDto convertMenuData(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, MenuSqsDto.class);
    }

}
