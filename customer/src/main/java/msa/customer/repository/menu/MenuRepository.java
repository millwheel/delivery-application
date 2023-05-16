package msa.customer.repository.menu;

import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;

import java.util.Optional;

public interface MenuRepository {
    String make(Menu menu);
    Optional<Menu> findById(String id);
    Optional<Menu> findByName(String name);
    void setName(String id, String name);
    void setPrice(String id, int price);
    void setDescription(String id, String description);
    void setRestaurant(String id, Restaurant restaurant);

}
