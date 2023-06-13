package msa.restaurant.dto;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.Store;

@Getter
@Setter
public class MenuRequestDto {
    private String name;
    private int price;
    private String description;
    private Store store;
}
