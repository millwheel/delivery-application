package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.menu.MenuPartResponseDto;
import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.dto.menu.MenuResponseDto;
import msa.restaurant.dto.menu.MenuSqsDto;
import msa.restaurant.entity.menu.Menu;
import msa.restaurant.message_queue.SendingMessageConverter;
import msa.restaurant.service.menu.MenuService;
import msa.restaurant.message_queue.SqsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/restaurant/store/{storeId}/menu")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

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
    public void addMenu(@RequestBody MenuRequestDto data,
                        @PathVariable String storeId) {
        Menu menu = menuService.createMenu(data, storeId);
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageForMenuInfo = sendingMessageConverter.createMessageToCreateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageForMenuInfo);
        sqsService.sendToRider(messageForMenuInfo);
    }

    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto menuInfo (@PathVariable String menuId) {
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new NullPointerException("Menu doesn't exist. " + menuId + " is not correct menu id.");
        }
        return new MenuResponseDto(menuOptional.get());
    }

    @PutMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMenu(@PathVariable String menuId,
                           @RequestBody MenuRequestDto data) {
        Menu menu = menuService.updateMenu(menuId, data);
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageToUpdateMenu = sendingMessageConverter.createMessageToUpdateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageToUpdateMenu);
        sqsService.sendToRider(messageToUpdateMenu);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@PathVariable String storeId,
                           @PathVariable String menuId) {
        if (!menuService.deleteMenu(menuId)){
            return;
        }
        String messageToDeleteMenu = sendingMessageConverter.createMessageToDeleteMenu(storeId, menuId);
        sqsService.sendToCustomer(messageToDeleteMenu);
        sqsService.sendToRider(messageToDeleteMenu);
    }

}
