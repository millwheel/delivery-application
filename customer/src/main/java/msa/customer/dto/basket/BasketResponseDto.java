package msa.customer.dto.basket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.menu.Menu;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BasketResponseDto {
    List<MenuInBasket> menuInBasketList;

    public BasketResponseDto(Basket basket) {
        List<String> menuIdList = basket.getMenuIdList();
        List<Integer> menuCountList = basket.getMenuCountList();
        List<Integer> menuPriceList = basket.getMenuPriceList();
        for (int i = 0; i < menuIdList.toArray().length; i++){
            MenuInBasket menuInBasket = new MenuInBasket();
            menuInBasket.setMenuId(menuIdList.get(i));
            menuInBasket.setCount(menuCountList.get(i));
            menuInBasket.setPrice(menuPriceList.get(i));
            menuInBasketList.add(menuInBasket);
        }
    }
}
