package msa.restaurant.dto.menu;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.MenuPartInfo;

@Setter
@Getter
public class MenuPartInfoResponseDto {
    private String name;
    private int price;

    public MenuPartInfoResponseDto(MenuPartInfo menuPartInfo) {
        name = menuPartInfo.getName();
        price = menuPartInfo.getPrice();
    }
}
