package msa.restaurant.repository.menu;

import msa.restaurant.entity.Menu;

import java.util.Optional;

public interface MenuRepository {
    String create(Menu menu);
    Optional<Menu> findById(String menuId);
    void updateName(String menuId, String name);
    void updatePrice(String menuId, int price);
    void updateDescription(String menuId, String description);
}
