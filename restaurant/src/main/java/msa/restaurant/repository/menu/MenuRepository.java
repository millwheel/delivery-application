package msa.restaurant.repository.menu;

import msa.restaurant.dto.MenuRequestDto;
import msa.restaurant.entity.Menu;

import java.util.Optional;

public interface MenuRepository {
    String create(Menu menu);
    Optional<Menu> findById(String menuId);
    void update(String menuId, MenuRequestDto data);
    void deleteById(String menuId);
}
