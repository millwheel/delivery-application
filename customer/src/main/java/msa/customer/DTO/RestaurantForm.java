package msa.customer.DTO;

import lombok.Getter;
import lombok.Setter;
import msa.customer.DAO.Menu;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import java.util.List;

@Getter
@Setter
public class RestaurantForm {

    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private GeoJsonPoint location;
    private String introduction;
    private List<Menu> menuList;
}
