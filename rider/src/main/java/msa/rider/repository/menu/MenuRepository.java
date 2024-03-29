package msa.rider.repository.menu;

import msa.rider.dto.menu.MenuSqsDto;
import msa.rider.entity.menu.Menu;

import java.util.Optional;

public interface MenuRepository {
    String create(Menu menu);
    Optional<Menu> findById(String menuId);
    void update(MenuSqsDto data);
    void deleteById(String menuId);
}
