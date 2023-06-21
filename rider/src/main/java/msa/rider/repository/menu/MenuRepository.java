package msa.rider.repository.menu;

import msa.rider.dto.MenuSqsDto;
import msa.rider.entity.Menu;

import java.util.Optional;

public interface MenuRepository {
    String create(Menu menu);
    void update(MenuSqsDto data);
    void deleteById(String menuId);
}