package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.dto.menu.MenuListResponseDto;
import msa.customer.dto.menu.MenuResponseDto;
import msa.customer.entity.menu.Menu;
import msa.customer.service.BasketService;
import msa.customer.service.MenuService;
import msa.customer.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer/{foodKind}/store/{storeId}/menu")
public class MenuController {

    private final StoreService storeService;
    private final MenuService menuService;
    private final BasketService basketService;

    public MenuController(StoreService storeService, MenuService menuService, BasketService basketService) {
        this.storeService = storeService;
        this.menuService = menuService;
        this.basketService = basketService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<MenuListResponseDto> showMenuList (@PathVariable String storeId){
        List<Menu> menuList = storeService.getMenuList(storeId);
        List<MenuListResponseDto> newMenuList = new ArrayList<>();
        menuList.forEach(menu -> {
            MenuListResponseDto menuListResponseDto = new MenuListResponseDto(menu);
            newMenuList.add(menuListResponseDto);
        });
        return newMenuList;
    }

    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto showMenu(@PathVariable String menuId){
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isPresent()){
            Menu menu = menuOptional.get();
            return new MenuResponseDto(menu);
        }
        throw new RuntimeException("menu doesn't exist.");
    }

    @PostMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void addToBasket(@RequestAttribute("cognitoUsername") String customerId,
                            @PathVariable String storeId,
                            @PathVariable String menuId,
                            @RequestBody int menuCount,
                            HttpServletResponse response) throws IOException {
        if (menuCount == 0){
            throw new IllegalArgumentException("menuCount should not be zero.");
        }
        if (menuCount >= 100000){
            throw new IllegalArgumentException("menuCount is too large.");
        }
        basketService.addToBasket(customerId, storeId, menuId, menuCount);
        response.sendRedirect("/customer/store/" + storeId + "/menu/" + menuId);
    }

}
