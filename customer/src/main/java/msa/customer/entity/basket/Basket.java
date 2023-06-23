package msa.customer.entity.basket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.customer.dto.basket.MenuInBasket;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Basket {
    private String basketId;
    private List<String> menuIdList;
    private List<String> menuNameList;
    private List<Integer> menuCountList;
    private List<Integer> menuPriceList;
    private String storeId;
    private int totalPrice;
}
