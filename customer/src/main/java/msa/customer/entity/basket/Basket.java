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
    List<String> menuName;
    List<Integer> menuCount;
    List<Integer> menuEachCount;
    List<Integer> menuPrice;
    int totalPrice;
}
