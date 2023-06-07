package msa.customer.DTO;

import lombok.Getter;
import lombok.Setter;
import msa.customer.DAO.Store;

@Getter
@Setter
public class MenuForm {
    private String menuId;
    private String name;
    private int price;
    private String description;
    private Store store;
}
