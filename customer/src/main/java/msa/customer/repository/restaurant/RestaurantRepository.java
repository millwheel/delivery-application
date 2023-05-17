package msa.customer.repository.restaurant;

import msa.customer.DAO.Location;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
    String make(Restaurant restaurant);
    Optional<Restaurant> findById(String id);
    List<Restaurant> findRestaurantNear(Location location);
    List<Restaurant> findAll();
    void setName(String id, String name);
    void setPhoneNumber(String id, String phoneNumber);
    void setAddress(String id, String address);
    void setAddressDetail(String id, String addressDetail);
    void setCoordinates(String id, Location location);
    void setIntroduction(String id, String introduction);
    void setMenuList(String id, List<Menu> menuList);
    void setOpen(String id, boolean open);
    void deleteAll();
}
