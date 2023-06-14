package msa.restaurant.service;

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
}
