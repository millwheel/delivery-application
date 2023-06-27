package msa.restaurant.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.restaurant.entity.order.OrderMenu;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderPartResponseDto {
    private String orderId;
    private String orderTime;
    private OrderStatus orderStatus;
    private String customerAddress;
    private List<OrderMenu> orderMenuList;
    private int totalPrice;

    public OrderPartResponseDto(Order order) {
        orderId = order.getOrderId();
        orderTime = order.getOrderTime();
        orderStatus = order.getOrderStatus();
        customerAddress = order.getCustomerAddress();
        orderMenuList = order.getOrderMenuList();
        totalPrice = order.getTotalPrice();
    }
}
