package msa.customer.repository.menu;

import msa.customer.dto.menu.MenuSqsDto;
import msa.customer.entity.menu.Menu;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {
    String createMenu(Menu menu);
    Optional<Menu> readMenu(String menuId);
    Optional<List<Menu>> readMenuList(String storeId);
    void updateMenu(MenuSqsDto data);
    void deleteMenu(String menuId);
}
