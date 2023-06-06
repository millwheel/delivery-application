package msa.restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.DAO.FoodKindType;
import msa.restaurant.DAO.Menu;
import org.springframework.data.geo.Point;

import java.util.List;


@Getter
@Setter
public class StoreForm {
    private String storeId;
    private String name;
    private FoodKindType foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;
    private String introduction;
    private List<Menu> menuList;
}
