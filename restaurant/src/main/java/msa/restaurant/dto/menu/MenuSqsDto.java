package msa.restaurant.dto.menu;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.menu.Menu;

@Setter
@Getter
public class MenuSqsDto {
    private String menuId;
    private String name;
    private int price;
    private String description;
    private String storeId;

    public MenuSqsDto(Menu menu) {
        menuId = menu.getMenuId();
        name = menu.getName();
        price = menu.getPrice();
        description = menu.getDescription();
        storeId = menu.getStoreId();
    }
}
