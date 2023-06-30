package msa.restaurant.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import msa.restaurant.entity.order.OrderMenu;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import org.springframework.data.geo.Point;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderDeserializer extends StdDeserializer {

    public OrderDeserializer() {
        this(null);
    }
    protected OrderDeserializer(Class vc) {
        super(vc);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String orderId = node.get("orderId").asText();
        String orderTime = node.get("orderTime").asText();
        OrderStatus orderStatus = OrderStatus.valueOf(node.get("orderStatus").asText());
        String customerId = node.get("customerId").asText();
        String customerName = node.get("customerName").asText();
        String customerPhoneNumber = node.get("customerPhoneNumber").asText();
        String customerAddress = node.get("customerAddress").asText();
        String customerAddressDetail = node.get("customerAddressDetail").asText();
        double cx = node.get("customerLocation").get("x").asDouble();
        double cy = node.get("customerLocation").get("y").asDouble();
        Point customerLocation = new Point(cx, cy);
        List<OrderMenu> orderMenuList = new ArrayList<>();
        node.get("menuInBasketList").forEach(eachData -> {
            OrderMenu orderMenu = new OrderMenu();
            orderMenu.setMenuId(eachData.get("menuId").asText());
            orderMenu.setMenuName(eachData.get("menuName").asText());
            orderMenu.setCount(eachData.get("count").asInt());
            orderMenu.setEachPrice(eachData.get("eachPrice").asInt());
            orderMenu.setPrice(eachData.get("price").asInt());
            orderMenuList.add(orderMenu);
        });
        int totalPrice = node.get("totalPrice").asInt();
        String storeId = node.get("storeId").asText();
        String storeName = node.get("storeName").asText();
        String storePhoneNumber = node.get("storePhoneNumber").asText();
        String storeAddress = node.get("storeAddress").asText();
        String storeAddressDetail = node.get("storeAddressDetail").asText();
        double sx = node.get("storeLocation").get("x").asDouble();
        double sy = node.get("storeLocation").get("y").asDouble();
        Point storeLocation = new Point(sx, sy);
        return new Order(orderId, orderTime, orderStatus, customerId, customerName, customerPhoneNumber,
                customerAddress, customerAddressDetail, customerLocation, orderMenuList, totalPrice,
                storeId, storeName, storePhoneNumber, storeAddress, storeAddressDetail, storeLocation);
    }
}
