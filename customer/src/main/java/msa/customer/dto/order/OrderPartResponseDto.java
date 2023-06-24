package msa.customer.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.customer.entity.basket.MenuInBasket;
import msa.customer.entity.order.Order;
import msa.customer.entity.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderPartResponseDto {
    private String orderId;
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private String storeName;
    private List<MenuInBasket> menuInBasketList;
    private int totalPrice;

    public OrderPartResponseDto(Order order) {
        orderId = order.getOrderId();
        orderTime = order.getOrderTime();
        orderStatus = order.getOrderStatus();
        storeName = order.getStoreName();
        menuInBasketList = order.getMenuInBasketList();
        totalPrice = order.getTotalPrice();
    }
}
