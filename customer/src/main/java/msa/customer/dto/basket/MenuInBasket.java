package msa.customer.dto.basket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenuInBasket {
    private String menuId;
    private int count;
    private int price;
}
