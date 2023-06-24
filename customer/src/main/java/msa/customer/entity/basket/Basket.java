package msa.customer.entity.basket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Basket {
    private String basketId;
    private List<MenuInBasket> menuInBasketList;
    private String storeId;
    private int totalPrice;
}
