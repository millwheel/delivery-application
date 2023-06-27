package msa.restaurant.entity.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class OrderMenu {
    private String menuId;
    private String menuName;
    private int count;
    private int eachPrice;
    private int price;
}
