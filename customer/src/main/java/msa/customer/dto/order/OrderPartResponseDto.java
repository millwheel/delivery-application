package msa.customer.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.customer.entity.basket.MenuInBasket;
import msa.customer.entity.order.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderPartResponseDto {
    private LocalDateTime orderTime;
    private OrderStatus orderStatus;
    private String orderId;
    private String storeName;
    private List<MenuInBasket> menuInBasketList;
    private int totalPrice;

}
