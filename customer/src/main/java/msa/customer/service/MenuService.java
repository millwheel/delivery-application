package msa.customer.service;

import msa.customer.dto.menu.MenuSqsDto;
import msa.customer.entity.Menu;
import msa.customer.repository.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public void createMenu(MenuSqsDto data){
        Menu menu = new Menu();
        menu.setMenuId(data.getMenuId());
        menu.setName(data.getName());
        menu.setPrice(data.getPrice());
        menu.setDescription(data.getDescription());
        menuRepository.create(menu);
    }

    public Optional<Menu> getMenu(String menuId){
        return menuRepository.findById(menuId);
    }

    public void updateMenu(MenuSqsDto data){
        menuRepository.update(data);
    }

    public void deleteMenu(String menuId){
        menuRepository.deleteById(menuId);
    }
}
