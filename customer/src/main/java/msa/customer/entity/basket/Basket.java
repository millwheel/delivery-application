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
<<<<<<< HEAD
//    private List<MenuInBasket> menuInBasketList;
    private List<String> menuIdList;
    private List<Integer> menuCountList;
    private List<Integer> menuPriceList;
=======
    private List<MenuInBasket> menuInBasketList;
>>>>>>> 8a3f8f325f90bd671483f95c516aed65b56d13d2
    private String storeId;
    private int totalPrice;
}
