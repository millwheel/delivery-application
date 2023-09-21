package msa.rider.message_queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import msa.rider.deserializer.OrderDeserializer;
import msa.rider.deserializer.PointMixin;
import msa.rider.deserializer.StoreDeserializer;
import msa.rider.dto.menu.MenuSqsDto;
import msa.rider.dto.store.StoreSqsDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.service.menu.MenuService;
import msa.rider.service.order.OrderService;
import msa.rider.service.store.StoreService;
import msa.rider.sse.SseService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReceivingMessageConverter {

    private final StoreService storeService;
    private final MenuService menuService;
    private final OrderService orderService;
    private final SseService sseService;

    @Autowired
    public ReceivingMessageConverter(StoreService storeService, MenuService menuService, OrderService orderService, SseService sseService) {
        this.storeService = storeService;
        this.menuService = menuService;
        this.orderService = orderService;
        this.sseService = sseService;
    }

    public void processMessage(String message) throws JsonProcessingException {
        JSONObject jsonObject = new JSONObject(message);
        if (jsonObject.get("dataType").equals("store")){
            processStoreData(jsonObject);
        } else if (jsonObject.get("dataType").equals("menu")) {
            processMenuData(jsonObject);
        } else if (jsonObject.get("dataType").equals("order")) {
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
            String storeId = (String)jsonObject.get("storeId");
            storeService.openStore(storeId);
        } else if (jsonObject.get("method").equals("close")){
            String storeId = (String)jsonObject.get("storeId");
            storeService.closeStore(storeId);
        } else if (jsonObject.get("method").equals("delete")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String storeId = (String) data.get("storeId");
            storeService.deleteStore(storeId);
        }
    }

    public StoreSqsDto convertStoreData(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Point.class, PointMixin.class);
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
        if (jsonObject.get("method").equals("request")){
            Order order = convertOrderData(jsonObject);
            orderService.createOrder(order);
            sseService.updateOrderFromSqs(order.getRiderId(), order.getOrderId());
        }else if (jsonObject.get("method").equals("change")) {
            JSONObject data = new JSONObject(jsonObject.get("data").toString());
            String orderId = data.getString("orderId");
            String riderId = data.getString("riderId");
            OrderStatus orderStatus = OrderStatus.valueOf((String) data.get("orderStatus"));
            orderService.changeOrderStatusFromOtherServer(orderId, orderStatus);
            sseService.updateOrderFromSqs(riderId, orderId);
        }
    }

    public Order convertOrderData(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Point.class, PointMixin.class);
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, Order.class);
    }

}
