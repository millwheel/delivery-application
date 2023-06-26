package msa.restaurant.dto.menu;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.menu.Menu;

@Setter
@Getter
public class MenuPartResponseDto {
    private String menuId;
    private String name;
    private int price;

    public MenuPartResponseDto(Menu menu) {
        menuId = menu.getMenuId();
        name = menu.getName();
        price = menu.getPrice();
    }
}
