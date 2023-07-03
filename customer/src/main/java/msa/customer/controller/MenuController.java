package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.dto.menu.MenuPartResponseDto;
import msa.customer.dto.menu.MenuResponseDto;
import msa.customer.entity.menu.Menu;
import msa.customer.entity.store.FoodKind;
import msa.customer.entity.store.Store;
import msa.customer.service.basket.BasketService;
import msa.customer.service.menu.MenuService;
import msa.customer.service.store.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer/{foodKind}/store/{storeId}/menu")
public class MenuController {

    private final MenuService menuService;
    private final BasketService basketService;
    private final StoreService storeService;

    public MenuController(MenuService menuService, BasketService basketService, StoreService storeService) {
        this.menuService = menuService;
        this.basketService = basketService;
        this.storeService = storeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuPartResponseDto> showMenuList (@PathVariable String storeId){
        List<Menu> menuList = menuService.getMenuList(storeId);
        List<MenuPartResponseDto> menuPartList = new ArrayList<>();
        menuList.forEach(menu -> {
            MenuPartResponseDto menuPartResponseDto = new MenuPartResponseDto(menu);
            menuPartList.add(menuPartResponseDto);
        });
        return menuPartList;
    }

    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto showMenu(@PathVariable String menuId){
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("menu doesn't exist.");
        }
        Menu menu = menuOptional.get();
        return new MenuResponseDto(menu);
    }

    @PostMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToBasket(@RequestAttribute("cognitoUsername") String customerId,
                            @PathVariable FoodKind foodKind,
                            @PathVariable String storeId,
                            @PathVariable String menuId,
                            @RequestBody int menuCount,
                            HttpServletResponse response) throws IOException {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if(storeOptional.isEmpty()){
            throw new RuntimeException("store doesn't exist.");
        }
        if (menuCount == 0){
            throw new IllegalArgumentException("menuCount should not be zero.");
        }
        if (menuCount >= 100000){
            throw new IllegalArgumentException("menuCount is too large.");
        }
        basketService.addToBasket(customerId, storeId, menuId, menuCount);
        response.sendRedirect("/customer/" + foodKind + "/store/" + storeId + "/menu");
    }

}
