package msa.restaurant.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.menu.MenuPartResponseDto;
import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.dto.menu.MenuResponseDto;
import msa.restaurant.entity.menu.Menu;
import msa.restaurant.service.menu.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/restaurant/store/{storeId}/menu")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuPartResponseDto> menuList(@PathVariable String storeId){
        List<Menu> menuList = menuService.getMenuList(storeId);
        List<MenuPartResponseDto> menuListDto = new ArrayList<>();
        menuList.forEach(menu -> {
            menuListDto.add(new MenuPartResponseDto(menu));
        });
        return menuListDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createMenu(@Validated @RequestBody MenuRequestDto data, @PathVariable String storeId) {
        return menuService.createMenu(data, storeId);
    }

    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto menuInfo (@PathVariable String storeId, @PathVariable String menuId) {
        Menu menu = menuService.getMenu(storeId, menuId);
        return new MenuResponseDto(menu);
    }

    @PatchMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto updateMenu(@RequestBody MenuRequestDto data,
                                      @PathVariable String storeId,
                                      @PathVariable String menuId) {
        Menu menu = menuService.updateMenu(storeId, menuId, data);
        return new MenuResponseDto(menu);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable String storeId,
                           @PathVariable String menuId) {
        menuService.deleteMenu(storeId, menuId);
    }

}
