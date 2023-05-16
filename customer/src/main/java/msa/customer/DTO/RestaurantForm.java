package msa.customer.DTO;

import lombok.Getter;
import lombok.Setter;
import msa.customer.DAO.Coordinates;
import msa.customer.DAO.Menu;

import java.util.List;

@Getter
@Setter
public class RestaurantForm {

    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Coordinates coordinates;
    private String introduction;
    private List<Menu> menuList;
}
