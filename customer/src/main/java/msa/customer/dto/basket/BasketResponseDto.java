package msa.customer.dto.basket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.basket.MenuInBasket;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BasketResponseDto {
    List<MenuInBasket> menuInBasketList;

    public BasketResponseDto(Basket basket) {
        menuInBasketList = basket.getMenuInBasketList();
    }
}
