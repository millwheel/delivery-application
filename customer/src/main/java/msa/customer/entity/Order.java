package msa.customer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document("order")
@NoArgsConstructor
public class Order {
    private String customerId;
    private String storeId;
    private int totalPrice;

    @MongoId
    private String orderId;
    private String customerId;
    private String customerName;
    private String customerAddress;
    private String customerAddressDetail;
    private String storeId;
    private String storeName;
    private String storeAddress;
    private String storeAddressDetail;
    private List<MenuPartInfo> menuList;
    private int totalPrice;
}
