package msa.customer.dto.menu;

import lombok.Getter;
import lombok.Setter;
import msa.customer.entity.menu.Menu;

import java.util.List;

@Setter
@Getter
public class MenuListResponseDto {
    private String menuId;
    private String name;
    private int price;

    public MenuListResponseDto(Menu menu) {
        menuId = menu.getMenuId();
        name = menu.getName();
        price = menu.getPrice();
    }
}
