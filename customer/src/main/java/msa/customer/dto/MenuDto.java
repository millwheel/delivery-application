package msa.customer.dto;

import lombok.Getter;
import lombok.Setter;
import msa.customer.entity.Store;

@Getter
@Setter
public class MenuDto {
    private String menuId;
    private String name;
    private int price;
    private String description;
    private Store store;
}