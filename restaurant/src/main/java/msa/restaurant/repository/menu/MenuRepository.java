package msa.restaurant.repository.menu;

import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.entity.menu.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    Menu create(Menu menu);
    Optional<Menu> readMenu(String menuId);
    List<Menu> readMenuList(String storeId);
    Menu update(String menuId, MenuRequestDto data);
    void delete(String menuId);
}
