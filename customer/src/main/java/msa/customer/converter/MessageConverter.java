package msa.customer.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.MenuSqsDto;
import msa.customer.dto.StoreSqsDto;
import msa.customer.service.MenuService;
import msa.customer.service.StoreService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageConverter {

    private final StoreService storeService;
    private final MenuService menuService;

    public MessageConverter(StoreService storeService, MenuService menuService) {
        this.storeService = storeService;
        this.menuService = menuService;
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
        } else if (jsonObject.get("dataType").equals("menu")) {
            if (jsonObject.get("method").equals("create")){
                MenuSqsDto menuSqsDto = convertMenuData(jsonObject);
                menuService.createStore(menuSqsDto);
                storeService.addToMenuList(menuSqsDto);
            } else if (jsonObject.get("method").equals("update")) {
                MenuSqsDto menuSqsDto = convertMenuData(jsonObject);
                menuService.updateStore(menuSqsDto);
                storeService.updateMenuFromList(menuSqsDto);
            } else if (jsonObject.get("method").equals("delete")){
                String menuId = (String) jsonObject.get("menuId");
                String storeId = (String) jsonObject.get("storeId");
                menuService.deleteStore(menuId);
                storeService.deleteMenuFromList(storeId, menuId);
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

    public MenuSqsDto convertMenuData(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, MenuSqsDto.class);
    }

}
