package msa.restaurant.entity;

import lombok.Getter;
@Getter
public class MenuPartInfo {
    private final String menuId;
    private final String name;
    private final int price;

    public MenuPartInfo(Menu menu) {
        menuId = menu.getMenuId();
        name = menu.getName();
        price = menu.getPrice();
    }
}
