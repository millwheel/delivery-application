package msa.restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.DAO.Restaurant;
import org.springframework.data.geo.Point;

import java.util.List;

@Setter
@Getter
public class ManagerForm {

    private String name;
    private String email;
    private String phoneNumber;
    private List<Restaurant> restaurantList;

}
