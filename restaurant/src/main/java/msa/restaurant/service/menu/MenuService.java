package msa.restaurant.service.menu;

import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.entity.menu.Menu;
import msa.restaurant.repository.menu.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public String createMenu(MenuRequestDto data, String storeId){
        Menu menu = new Menu();
        menu.setName(data.getName());
        menu.setPrice(data.getPrice());
        menu.setDescription(data.getDescription());
        menu.setStoreId(storeId);
        return menuRepository.create(menu);
    }

    public Optional<Menu> getMenu(String menuId){
        return menuRepository.readMenu(menuId);
    }

    public List<Menu> getMenuList(String storeId){
        return menuRepository.readMenuList(storeId);
    }

    public void updateMenu(String menuId, MenuRequestDto data){
        menuRepository.update(menuId, data);
    }

    public void deleteMenu(String menuId){
        menuRepository.deleteById(menuId);
    }
}
