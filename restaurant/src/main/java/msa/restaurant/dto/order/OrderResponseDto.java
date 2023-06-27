package msa.restaurant.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.restaurant.entity.order.OrderMenu;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private String orderTime;
    private OrderStatus orderStatus;
    private String customerPhoneNumber;
    private String customerAddress;
    private String customerAddressDetail;
    private List<OrderMenu> orderMenuList;
    private int totalPrice;
    private String storeName;
    private String storePhoneNumber;
    private String storeAddress;
    private String storeAddressDetail;
    private String riderName;
    private String riderPhoneNumber;

    public OrderResponseDto(Order order) {
        orderTime = order.getOrderTime();
        orderStatus = order.getOrderStatus();
        customerPhoneNumber = order.getCustomerPhoneNumber();
        customerAddress = order.getCustomerAddress();
        customerAddressDetail = order.getCustomerAddressDetail();
        orderMenuList = order.getOrderMenuList();
        totalPrice = order.getTotalPrice();
        storeName = order.getStoreName();
        storePhoneNumber = order.getStorePhoneNumber();
        storeAddress = order.getStoreAddress();
        storeAddressDetail = order.getStoreAddressDetail();
    }
}
