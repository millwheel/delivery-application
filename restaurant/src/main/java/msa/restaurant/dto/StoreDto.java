package msa.restaurant.dto;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.FoodKindType;
import msa.restaurant.entity.Menu;
import org.springframework.data.geo.Point;

import java.util.List;


@Getter
@Setter
public class StoreDto {
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
