package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.menu.MenuPartResponseDto;
import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.dto.menu.MenuResponseDto;
import msa.restaurant.dto.menu.MenuSqsDto;
import msa.restaurant.entity.menu.Menu;
import msa.restaurant.sqs.SendingMessageConverter;
import msa.restaurant.service.menu.MenuService;
import msa.restaurant.sqs.SqsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/restaurant/store/{storeId}/menu")
public class MenuController {
    private final MenuService menuService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

    public MenuController(MenuService menuService,  SendingMessageConverter sendingMessageConverter, SqsService sqsService) {
        this.menuService = menuService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.sqsService = sqsService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuPartResponseDto> menuList(@RequestAttribute("cognitoUsername") String managerId,
                                              @PathVariable String storeId){
        List<Menu> menuList = menuService.getMenuList(storeId);
        List<MenuPartResponseDto> menuListDto = new ArrayList<>();
        menuList.forEach(menu -> {
            menuListDto.add(new MenuPartResponseDto(menu));
        });
        return menuListDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addMenu(@RequestAttribute("cognitoUsername") String managerId,
                        @RequestBody MenuRequestDto data,
                        @PathVariable String storeId,
                        HttpServletResponse response) throws IOException {
        String menuId = menuService.createMenu(data, storeId);
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Menu creation failed.");
        }
        Menu menu = menuOptional.get();
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageForMenuInfo = sendingMessageConverter.createMessageToCreateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageForMenuInfo);
        sqsService.sendToRider(messageForMenuInfo);
    }

    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto menuInfo (@RequestAttribute("cognitoUsername") String managerId,
                                     @PathVariable String storeId,
                                     @PathVariable String menuId) {
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new NullPointerException("Menu doesn't exist. " + menuId + " is not correct menu id.");
        }
        return new MenuResponseDto(menuOptional.get());
    }

    @PutMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMenu(@RequestAttribute("cognitoUsername") String managerId,
                           @PathVariable String storeId,
                           @PathVariable String menuId,
                           @RequestBody MenuRequestDto data,
                           HttpServletResponse response) throws IOException {
        menuService.updateMenu(menuId, data);
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new NullPointerException("Menu doesn't exist. " + menuId + " is not correct menu id.");
        }
        Menu menu = menuOptional.get();
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageToUpdateMenu = sendingMessageConverter.createMessageToUpdateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageToUpdateMenu);
        sqsService.sendToRider(messageToUpdateMenu);
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenu(@RequestAttribute("cognitoUsername") String managerId,
                           @PathVariable String storeId,
                           @PathVariable String menuId,
                           HttpServletResponse response) throws IOException {
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new NullPointerException("Menu doesn't exist. " + menuId + " is not correct menu id.");
        }
        menuService.deleteMenu(menuId);
        String messageToDeleteMenu = sendingMessageConverter.createMessageToDeleteMenu(storeId, menuId);
        sqsService.sendToCustomer(messageToDeleteMenu);
        sqsService.sendToRider(messageToDeleteMenu);
    }

}
