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
    void update(String restaurantId, RestaurantForm data);
    Optional<Restaurant> findById(String restaurantId);
    List<Restaurant> findAll();
    void updateName(String restaurantId, String name);
    void updateFoodKind(String restaurantId, FoodKindType foodKind);
    void updatePhoneNumber(String restaurantId, String phoneNumber);
    void updateAddress(String restaurantId, String address);
    void updateAddressDetail(String restaurantId, String addressDetail);
    void updateLocation(String restaurantId, Point location);
    void updateIntroduction(String restaurantId, String introduction);
    void updateMenuList(String restaurantId, List<Menu> menuList);
    void updateOpenStatus(String restaurantId, boolean open);
    void deleteAll();
}
