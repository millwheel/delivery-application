package msa.customer.dto.basket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BasketRequestDto {
    private String menuName;
    private int menuCount;
}
