package msa.restaurant.service;

import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.entity.Menu;
import msa.restaurant.repository.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Optional<Menu> getMenu(String menuId){
        return menuRepository.findById(menuId);
    }

    public String createMenu(String storeId, MenuRequestDto data){
        Menu menu = new Menu();
        menu.setName(data.getName());
        menu.setStoreId(storeId);
        menu.setPrice(data.getPrice());
        menu.setDescription(data.getDescription());
        return menuRepository.create(menu);
    }

    public void updateMenu(String menuId, MenuRequestDto data){
        menuRepository.update(menuId, data);
    }

    public void deleteMenu(String menuId){
        menuRepository.deleteById(menuId);
    }
}
