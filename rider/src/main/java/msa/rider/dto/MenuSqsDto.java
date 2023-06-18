package msa.rider.dto;

import lombok.Getter;

@Getter
public class MenuSqsDto {
    private String menuId;
    private String name;
    private int price;
    private String description;
    private String storeId;
}
