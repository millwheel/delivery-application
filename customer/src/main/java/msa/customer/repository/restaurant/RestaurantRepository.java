package msa.customer.repository.restaurant;

import msa.customer.DAO.FoodKindType;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import msa.customer.DTO.RestaurantForm;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    String create(Restaurant restaurant);
    void update(String id, RestaurantForm data);
    Optional<Restaurant> findById(String id);
    List<Restaurant> findRestaurantNear(Point location, FoodKindType foodKind);
    List<Restaurant> findAll();
    void updateName(String id, String name);
    void updateFoodKind(String id, FoodKindType foodKind);
    void updatePhoneNumber(String id, String phoneNumber);
    void updateAddress(String id, String address);
    void updateAddressDetail(String id, String addressDetail);
    void updateLocation(String id, Point location);
    void updateIntroduction(String id, String introduction);
    void updateMenuList(String id, List<Menu> menuList);
    void setOpen(String id, boolean open);
    void deleteAll();
}
