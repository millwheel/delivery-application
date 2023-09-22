package msa.customer.controller;

import lombok.AllArgsConstructor;
import msa.customer.dto.menu.MenuPartResponseDto;
import msa.customer.dto.menu.MenuResponseDto;
import msa.customer.entity.menu.Menu;
import msa.customer.exception.MenuLargeException;
import msa.customer.exception.MenuZeroException;
import msa.customer.service.basket.BasketService;
import msa.customer.service.menu.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer/{foodKind}/store/{storeId}/menu")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final BasketService basketService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuPartResponseDto> showMenuList (@PathVariable String storeId){
        List<Menu> menus = menuService.getMenuList(storeId);
        List<MenuPartResponseDto> menusDto = new ArrayList<>();
        menus.forEach(menu -> {
            menusDto.add(new MenuPartResponseDto(menu));
        });
        return menusDto;
    }

    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto showMenu(@PathVariable String storeId, @PathVariable String menuId){
        Menu menu = menuService.getMenu(menuId);
        return new MenuResponseDto(menu);
    }

    @PostMapping("/{menuId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addToBasket(@RequestAttribute("cognitoUsername") String customerId,
                            @PathVariable String storeId,
                            @PathVariable String menuId,
                            @RequestBody int menuCount) {
        if (menuCount == 0){
            throw new MenuZeroException();
        }
        if (menuCount >= 100000){
            throw new MenuLargeException();
        }
        basketService.addToBasket(customerId, storeId, menuId, menuCount);
    }

}
