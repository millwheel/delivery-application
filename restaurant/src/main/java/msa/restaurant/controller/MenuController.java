package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.dto.menu.MenuResponseDto;
import msa.restaurant.entity.Menu;
import msa.restaurant.converter.MessageConverter;
import msa.restaurant.entity.MenuPartInfo;
import msa.restaurant.service.MenuService;
import msa.restaurant.service.SqsService;
import msa.restaurant.service.StoreService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/restaurant/{storeId}/menu")
public class MenuController {

    private final StoreService storeService;
    private final MenuService menuService;
    private final MessageConverter messageConverter;
    private final SqsService sqsService;

    public MenuController(StoreService storeService, MenuService menuService, MessageConverter messageConverter, SqsService sqsService) {
        this.storeService = storeService;
        this.menuService = menuService;
        this.messageConverter = messageConverter;
        this.sqsService = sqsService;
    }

    @GetMapping("/list")
    public List<MenuResponseDto> menuList(@PathVariable String storeId){
        List<Menu> menuList = storeService.getMenuList(storeId).orElseGet(ArrayList::new);
        List<MenuResponseDto> menuResponseDtoList = new ArrayList<>();
        menuList.forEach(menu -> {
            menuResponseDtoList.add(new MenuResponseDto(menu));
        });
        return menuResponseDtoList;
    }

    @GetMapping("/info/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto menuInfo (@PathVariable String menuId) {
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isPresent()){
            Menu menu = menuOptional.get();
            return new MenuResponseDto(menu);
        }
        throw new RuntimeException("Cannot find menu by menu-id");
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMenu(@RequestBody MenuRequestDto data,
                        HttpServletResponse response) throws IOException {
        String menuId = menuService.createMenu(data);
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Cannot ")
        }
        Menu menu = menuOptional.get();
        MenuPartInfo menuPartInfo = new MenuPartInfo(menu);
        storeService.
        response.sendRedirect("/restaurant/{storeId}/menu/list");
    }

}
