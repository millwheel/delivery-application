package msa.customer.entity.basket;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class MenuInBasket {
    private String menuId;
    private String menuName;
    private int count;
    private int eachPrice;
    private int price;

    @Builder
    public MenuInBasket(String menuId, String menuName, int count, int eachPrice, int price) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.count = count;
        this.eachPrice = eachPrice;
        this.price = price;
    }
}
