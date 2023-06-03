package msa.restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.DAO.FoodKindType;
import org.springframework.data.geo.Point;

import java.util.List;

@Getter
@Setter
public class RestaurantForm {
    private String name;
    private FoodKindType foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;
    private String introduction;
}
