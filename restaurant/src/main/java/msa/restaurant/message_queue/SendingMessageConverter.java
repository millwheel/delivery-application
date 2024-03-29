package msa.restaurant.message_queue;

import msa.restaurant.dto.menu.MenuSqsDto;
import msa.restaurant.dto.store.StoreSqsDto;
import msa.restaurant.entity.order.Order;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class SendingMessageConverter {
    public String createMessageToCreateStore(StoreSqsDto storeSqsDto){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject(storeSqsDto);
        jsonObject.put("dataType", "store");
        jsonObject.put("method", "create");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToUpdateStore(StoreSqsDto storeSqsDto){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject(storeSqsDto);
        jsonObject.put("dataType", "store");
        jsonObject.put("method", "update");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToOpenStore(String storeId){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "store");
        jsonObject.put("method", "open");
        data.put("storeId", storeId);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToCloseStore(String storeId){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "store");
        jsonObject.put("method", "close");
        data.put("storeId", storeId);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToDeleteStore(String storeId){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "store");
        jsonObject.put("method", "delete");
        data.put("storeId", storeId);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToCreateMenu(MenuSqsDto menuSqsDto){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject(menuSqsDto);
        jsonObject.put("dataType", "menu");
        jsonObject.put("method", "create");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToUpdateMenu(MenuSqsDto menuSqsDto){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject(menuSqsDto);
        jsonObject.put("dataType", "menu");
        jsonObject.put("method", "update");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToDeleteMenu(String storeId, String menuId){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "menu");
        jsonObject.put("method", "delete");
        data.put("menuId", menuId);
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToAcceptOrder(Order order){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "order");
        jsonObject.put("method", "change");
        data.put("customerId", order.getCustomerId());
        data.put("orderId", order.getOrderId());
        data.put("orderStatus", order.getOrderStatus());
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToRequestOrder(Order order){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject(order);
        jsonObject.put("dataType", "order");
        jsonObject.put("method", "request");
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

    public String createMessageToChangeOrderStatus(Order order){
        JSONObject jsonObject = new JSONObject();
        JSONObject data = new JSONObject();
        jsonObject.put("dataType", "order");
        jsonObject.put("method", "change");
        data.put("customerId", order.getCustomerId());
        data.put("riderId", order.getRiderId());
        data.put("orderId", order.getOrderId());
        data.put("orderStatus", order.getOrderStatus());
        jsonObject.put("data", data);
        return jsonObject.toString();
    }

}
