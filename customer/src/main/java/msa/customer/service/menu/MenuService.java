package msa.customer.service.menu;

import msa.customer.dto.menu.MenuSqsDto;
import msa.customer.entity.menu.Menu;
import msa.customer.entity.store.Store;
import msa.customer.repository.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        menu.setStoreId(data.getStoreId());
        menuRepository.createMenu(menu);
    }

    public Optional<Menu> getMenu(String menuId){
        return menuRepository.readMenu(menuId);
    }

    public List<Menu> getMenuList(String storeId){
        return menuRepository.readMenuList(storeId);
    }

    public void updateMenu(MenuSqsDto data){
        menuRepository.updateMenu(data);
    }

    public void deleteMenu(String menuId){
        menuRepository.deleteMenu(menuId);
    }
}
