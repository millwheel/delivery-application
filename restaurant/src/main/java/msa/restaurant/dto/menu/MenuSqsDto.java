package msa.restaurant.dto.menu;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.Menu;

@Setter
@Getter
public class MenuSqsDto {
    private String menuId;
    private String name;
    private int price;
    private String description;

    public MenuSqsDto(Menu menu) {
        menuId = menu.getMenuId();
        name = menu.getName();
        price = menu.getPrice();
        description = menu.getDescription();
    }
}
