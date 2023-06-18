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

    public void createStore(MenuSqsDto data){
        Menu menu = new Menu();
        menu.setMenuId(data.getMenuId());
        menu.setName(data.getName());
        menu.setPrice(data.getPrice());
        menu.setDescription(data.getDescription());
        menuRepository.create(menu);
    }

    public void updateStore(MenuSqsDto data){
        menuRepository.update(data);
    }

    public void deleteStore(String menuId){
        menuRepository.deleteById(menuId);
    }
}
