package msa.restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.geo.Point;

@Getter
@Setter
public class RestaurantForm {

    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point coordinates;
}
