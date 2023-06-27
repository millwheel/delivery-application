package msa.rider.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MenuSqsDto {
    private String menuId;
    private String name;
    private int price;
    private String description;
    private String storeId;
}
