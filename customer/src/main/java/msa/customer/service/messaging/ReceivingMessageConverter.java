package msa.customer.service.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.menu.MenuSqsDto;
import msa.customer.dto.store.StoreSqsDto;
import msa.customer.entity.order.OrderStatus;
import msa.customer.service.menu.MenuService;
import msa.customer.service.order.OrderService;
import msa.customer.service.store.StoreService;
import msa.customer.deserializer.StoreDeserializer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceivingMessageConverter {

    private final StoreService storeService;
    private final MenuService menuService;
    private final OrderService orderService;

    @Autowired
    public ReceivingMessageConverter(StoreService storeService, MenuService menuService, OrderService orderService) {
        this.storeService = storeService;
        this.menuService = menuService;
        this.orderService = orderService;
    }

    public void processMessage(String message) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(message);
        if (jsonObject.get("dataType").equals("store")){
            processStoreData(jsonObject);
        } else if (jsonObject.get("dataType").equals("menu")) {
            processMenuData(jsonObject);
        } else if (jsonObject.get("dataType").equals("order")){
            processOrderData(jsonObject);
        }
    }

    public void processStoreData(JSONObject jsonObject) throws JsonProcessingException {
        if (jsonObject.get("method").equals("create")){
            StoreSqsDto storeSqsDto = convertStoreData(jsonObject);
            storeService.createStore(storeSqsDto);
        } else if (jsonObject.get("method").equals("update")) {
            StoreSqsDto storeSqsDto = convertStoreData(jsonObject);
            storeService.updateStore(storeSqsDto);
        } else if (jsonObject.get("method").equals("open")){
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String storeId = (String) data.get("storeId");
            storeService.openStore(storeId);
        } else if (jsonObject.get("method").equals("close")){
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String storeId = (String) data.get("storeId");
            storeService.closeStore(storeId);
        } else if (jsonObject.get("method").equals("delete")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String storeId = (String) data.get("storeId");
            storeService.deleteStore(storeId);
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

    public void processMenuData(JSONObject jsonObject) throws JsonProcessingException {
        if (jsonObject.get("method").equals("create")){
            MenuSqsDto menuSqsDto = convertMenuData(jsonObject);
            menuService.createMenu(menuSqsDto);
        } else if (jsonObject.get("method").equals("update")) {
            MenuSqsDto menuSqsDto = convertMenuData(jsonObject);
            menuService.updateMenu(menuSqsDto);
        } else if (jsonObject.get("method").equals("delete")){
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String menuId = (String) data.get("menuId");
            menuService.deleteMenu(menuId);
        }
    }

    public MenuSqsDto convertMenuData(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, MenuSqsDto.class);
    }

    public void processOrderData(JSONObject jsonObject) throws JsonProcessingException {
        if (jsonObject.get("method").equals("change")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = (String) data.get("orderId");
            OrderStatus orderStatus = OrderStatus.valueOf((String) data.get("orderStatus"));
            orderService.changeOrderStatusFromOtherServer(orderId, orderStatus);
        }
    }


}
