package msa.customer.DTO;

import lombok.Getter;
import lombok.Setter;
import msa.customer.DAO.FoodKindType;
import msa.customer.DAO.Menu;
import org.springframework.data.geo.Point;
import java.util.List;

@Getter
@Setter
public class RestaurantForm {
    private String id;
    private String name;
    private FoodKindType foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;
    private String introduction;
    private List<Menu> menuList;
}
