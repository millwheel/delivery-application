package msa.restaurant.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MenuInBasket {
    private String menuId;
    private String menuName;
    private int count;
    private int eachPrice;
    private int price;
}
