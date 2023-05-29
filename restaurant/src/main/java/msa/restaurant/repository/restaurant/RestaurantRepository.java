package msa.restaurant.repository.restaurant;

import msa.restaurant.DAO.FoodKindType;
import msa.restaurant.DAO.Menu;
import msa.restaurant.DAO.Restaurant;
import msa.restaurant.DTO.RestaurantForm;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    String create(Restaurant restaurant);
    void update(String id, RestaurantForm data);
    Optional<Restaurant> findById(String id);
    List<Restaurant> findAll();
    void updateName(String id, String name);
    void updateFoodKind(String id, FoodKindType foodKind);
    void updatePhoneNumber(String id, String phoneNumber);
    void updateAddress(String id, String address);
    void updateAddressDetail(String id, String addressDetail);
    void updateLocation(String id, Point location);
    void updateIntroduction(String id, String introduction);
    void updateMenuList(String id, List<Menu> menuList);
    void updateOpenStatus(String id, boolean open);
    void deleteAll();
}
