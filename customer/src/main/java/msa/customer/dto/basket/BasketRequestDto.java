package msa.customer.dto.basket;

import lombok.Getter;
import lombok.Setter;
import msa.customer.entity.basket.MenuInBasket;

import java.util.List;

@Getter
@Setter
public class BasketRequestDto {
    private List<MenuInBasket> menu;
}
