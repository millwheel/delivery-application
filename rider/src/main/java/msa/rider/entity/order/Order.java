package msa.rider.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
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
    private String orderTime;
    private OrderStatus orderStatus;
    private String customerId;
    private String customerName;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerAddressDetail;
    private Point customerLocation;
    private List<OrderMenu> orderMenuList;
    private int totalPrice;
    private String storeId;
    private String storeName;
    private String storePhoneNumber;
    private String storeAddress;
    private String storeAddressDetail;
    @GeoSpatialIndexed(type= GeoSpatialIndexType.GEO_2DSPHERE)
    private Point storeLocation;
    @Indexed
    private String riderId;
    private String riderName;
    private String riderPhoneNumber;
    private Point riderLocation;

    @Version
    private Long version;

    public Order(String orderId, String orderTime, OrderStatus orderStatus, String customerId, String customerName, String customerPhoneNumber, String customerAddress, String customerAddressDetail, Point customerLocation, List<OrderMenu> orderMenuList, int totalPrice, String storeId, String storeName, String storePhoneNumber, String storeAddress, String storeAddressDetail, Point storeLocation) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhoneNumber = customerPhoneNumber;
        this.customerAddress = customerAddress;
        this.customerAddressDetail = customerAddressDetail;
        this.customerLocation = customerLocation;
        this.orderMenuList = orderMenuList;
        this.totalPrice = totalPrice;
        this.storeId = storeId;
        this.storeName = storeName;
        this.storePhoneNumber = storePhoneNumber;
        this.storeAddress = storeAddress;
        this.storeAddressDetail = storeAddressDetail;
        this.storeLocation = storeLocation;
    }
}
