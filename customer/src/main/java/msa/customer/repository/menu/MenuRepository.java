package msa.customer.repository.menu;

import msa.customer.dto.menu.MenuSqsDto;
import msa.customer.entity.Menu;

import java.util.Optional;

public interface MenuRepository {
    String create(Menu menu);
    Optional<Menu> findById(String menuId);
    void update(MenuSqsDto data);
    void deleteById(String menuId);
}
