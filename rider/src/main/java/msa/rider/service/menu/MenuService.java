package msa.rider.service.menu;

import msa.rider.dto.menu.MenuSqsDto;
import msa.rider.entity.menu.Menu;
import msa.rider.repository.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MenuService {

    private final MenuRepository repository;

    public MenuService(MenuRepository menuRepository) {
        this.repository = menuRepository;
    }

    public void createMenu(MenuSqsDto data){
        Menu menu = new Menu();
        menu.setMenuId(data.getMenuId());
        menu.setName(data.getName());
        menu.setPrice(data.getPrice());
        menu.setDescription(data.getDescription());
        menu.setStoreId(data.getStoreId());
        repository.create(menu);
    }

    public Optional<Menu> getMenu(String menuId){
        return repository.findById(menuId);
    }

    public void updateMenu(MenuSqsDto data){
        repository.update(data);
    }

    public void deleteMenu(String menuId){
        repository.deleteById(menuId);
    }
}
