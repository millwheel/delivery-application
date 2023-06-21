package msa.customer.controller;

import msa.customer.dto.menu.MenuResponseDto;
import msa.customer.entity.menu.Menu;
import msa.customer.entity.menu.MenuPartInfo;
import msa.customer.service.MenuService;
import msa.customer.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer/store/{storeId}")
public class MenuController {

    private final StoreService storeService;
    private final MenuService menuService;

    public MenuController(StoreService storeService, MenuService menuService) {
        this.storeService = storeService;
        this.menuService = menuService;
    }

    @GetMapping("/menu-list")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuPartInfo> showMenuList (@PathVariable String storeId){
        return storeService.getMenuList(storeId);
    }

    @GetMapping("/menu/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto showMenu(@PathVariable String menuId){
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isPresent()){
            Menu menu = menuOptional.get();
            return new MenuResponseDto(menu);
        }
        throw new RuntimeException("menu doesn't exist.");
    }
}
