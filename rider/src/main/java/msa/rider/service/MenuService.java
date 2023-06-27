package msa.rider.service;

import msa.rider.dto.MenuSqsDto;
import msa.rider.entity.Menu;
import msa.rider.repository.menu.MenuRepository;
import org.springframework.stereotype.Service;

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
        menuRepository.create(menu);
    }

    public void updateMenu(MenuSqsDto data){
        menuRepository.update(data);
    }

    public void deleteMenu(String menuId){
        menuRepository.deleteById(menuId);
    }
}
