package msa.restaurant.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Document("order")
@NoArgsConstructor
public class Order {
    @MongoId
    private String orderId;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private String customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerAddressDetail;
    private Point customerLocation;
    private List<MenuInBasket> menuInBasketList;
    private int totalPrice;
    @Indexed
    private String storeId;
    private String storeName;
    private String storePhoneNumber;
    private String storeAddress;
    private String storeAddressDetail;
    private Point storeLocation;
    private int storeOrderNumber;
    private String riderId;
    private String riderPhoneNumber;
}
