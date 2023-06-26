package msa.restaurant.repository.menu;

import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.entity.menu.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    String create(Menu menu);
    Optional<Menu> readMenu(String menuId);
    Optional<List<Menu>> readMenuList(String storeId);
    void update(String menuId, MenuRequestDto data);
    void deleteById(String menuId);
}
