package msa.customer.dto.menu;

import lombok.Getter;

@Getter
public class MenuSqsDto {
    private String menuId;
    private String name;
    private int price;
    private String description;
    private String storeId;
}
