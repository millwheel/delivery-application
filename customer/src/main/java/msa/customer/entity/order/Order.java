package msa.customer.entity.order;

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
    @MongoId
    private String orderId;
    private String customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerAddressDetail;
    private Point customerLocation;
    private List<String> menuIdList;
    private List<String> menuNameList;
    private List<Integer> menuCountList;
    private List<Integer> menuPriceList;
    private int totalPrice;
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
