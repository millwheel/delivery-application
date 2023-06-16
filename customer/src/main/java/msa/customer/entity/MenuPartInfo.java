package msa.customer.entity;

import lombok.Getter;
import msa.customer.dto.MenuSqsDto;

@Getter
public class MenuPartInfo {
    private final String menuId;
    private final String name;
    private final int price;

    public MenuPartInfo(MenuSqsDto data) {
        menuId = data.getMenuId();
        name = data.getName();
        price = data.getPrice();
    }
}
