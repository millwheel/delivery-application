package msa.restaurant.dto.menu;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.menu.Menu;

@Setter
@Getter
public class MenuResponseDto {
    private String name;
    private int price;
    private String description;

    public MenuResponseDto(Menu menu) {
        name = menu.getName();
        price = menu.getPrice();
        description = menu.getDescription();
    }
}
