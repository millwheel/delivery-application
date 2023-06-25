package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.menu.MenuPartResponseDto;
import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.dto.menu.MenuResponseDto;
import msa.restaurant.dto.menu.MenuSqsDto;
import msa.restaurant.entity.Menu;
import msa.restaurant.converter.MessageConverter;
import msa.restaurant.service.MenuService;
import msa.restaurant.service.SqsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/restaurant/menu")
public class MenuController {
    private final MenuService menuService;
    private final MessageConverter messageConverter;
    private final SqsService sqsService;

    public MenuController(MenuService menuService, MessageConverter messageConverter, SqsService sqsService) {
        this.menuService = menuService;
        this.messageConverter = messageConverter;
        this.sqsService = sqsService;
    }

    @GetMapping("{storeId}/list")
    public List<MenuPartResponseDto> menuList(@PathVariable String storeId){
        Optional<List<Menu>> menuListOptional = menuService.getMenuList(storeId);
        if (menuListOptional.isEmpty()){
            throw new RuntimeException("no menu list");
        }
        List<Menu> menuList = menuListOptional.get();
        List<MenuPartResponseDto> menuResponseDtoList = new ArrayList<>();
        menuList.forEach(menu -> {
            menuResponseDtoList.add(new MenuPartResponseDto(menu));
        });
        return menuResponseDtoList;
    }

    @GetMapping("{storeId}/info/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto menuInfo (@PathVariable String menuId) {
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isPresent()){
            Menu menu = menuOptional.get();
            return new MenuResponseDto(menu);
        }
        throw new RuntimeException("Menu doesn't exist.");
    }

    @PostMapping("{storeId}/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMenu(@RequestBody MenuRequestDto data,
                        @PathVariable String storeId,
                        HttpServletResponse response) throws IOException {
        String menuId = menuService.createMenu(data, storeId);
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Created menu doesn't exist.");
        }
        Menu menu = menuOptional.get();
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageForMenuInfo = messageConverter.createMessageToCreateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageForMenuInfo);
        sqsService.sendToRider(messageForMenuInfo);
        response.sendRedirect("/restaurant/menu/" + storeId + "/list");
    }

    @PutMapping("{storeId}/update/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMenu(@PathVariable String storeId,
                           @PathVariable String menuId,
                           @RequestBody MenuRequestDto data,
                           HttpServletResponse response) throws IOException {
        menuService.updateMenu(menuId, data);
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Updated menu doesn't exist.");
        }
        Menu menu = menuOptional.get();
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageToUpdateMenu = messageConverter.createMessageToUpdateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageToUpdateMenu);
        sqsService.sendToRider(messageToUpdateMenu);
        response.sendRedirect("/restaurant/menu/" + storeId + "/list");
    }

    @DeleteMapping("{storeId}/delete/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMenu(@PathVariable String storeId,
                           @PathVariable String menuId,
                           HttpServletResponse response) throws IOException {
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Menu doesn't exist.");
        }
        menuService.deleteMenu(menuId);
        String messageToDeleteMenu = messageConverter.createMessageToDeleteMenu(storeId, menuId);
        sqsService.sendToCustomer(messageToDeleteMenu);
        sqsService.sendToRider(messageToDeleteMenu);
        response.sendRedirect("/restaurant/menu/" + storeId + "/list");
    }

}
