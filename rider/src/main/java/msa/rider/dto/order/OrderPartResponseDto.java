package msa.rider.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderMenu;
import msa.rider.entity.order.OrderStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderPartResponseDto {
    private String orderId;
    private String orderTime;
    private OrderStatus orderStatus;
    private String customerAddress;
    private String storeAddress;
    private List<OrderMenu> orderMenuList;
    private int totalPrice;

    public OrderPartResponseDto(Order order) {
        orderId = order.getOrderId();
        orderTime = order.getOrderTime();
        orderStatus = order.getOrderStatus();
        customerAddress = order.getCustomerAddress();
        storeAddress = order.getStoreAddress();
        orderMenuList = order.getOrderMenuList();
        totalPrice = order.getTotalPrice();
    }
}
