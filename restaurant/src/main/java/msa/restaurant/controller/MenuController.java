package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.menu.MenuPartResponseDto;
import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.dto.menu.MenuResponseDto;
import msa.restaurant.dto.menu.MenuSqsDto;
import msa.restaurant.entity.menu.Menu;
import msa.restaurant.entity.store.Store;
import msa.restaurant.service.messaging.SendingMessageConverter;
import msa.restaurant.service.menu.MenuService;
import msa.restaurant.service.messaging.SqsService;
import msa.restaurant.service.store.StoreService;
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
    private final StoreService storeService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

    public MenuController(MenuService menuService, StoreService storeService, SendingMessageConverter sendingMessageConverter, SqsService sqsService) {
        this.menuService = menuService;
        this.storeService = storeService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.sqsService = sqsService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MenuPartResponseDto> menuList(@RequestAttribute("cognitoUsername") String managerId,
                                              @PathVariable String storeId){
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if(storeOptional.isEmpty()){
            throw new RuntimeException("store doesn't exist.");
        }
        Store store = storeOptional.get();
        if(store.getManagerId().equals(managerId)){
            throw new RuntimeException("This store doesn't belong to this manager.");
        }
        Optional<List<Menu>> menuListOptional = menuService.getMenuList(storeId);
        if (menuListOptional.isEmpty()){
            throw new RuntimeException("no menu list");
        }
        List<Menu> menuList = menuListOptional.get();
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
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if(storeOptional.isEmpty()){
            throw new RuntimeException("store doesn't exist.");
        }
        Store store = storeOptional.get();
        if(store.getManagerId().equals(managerId)){
            throw new RuntimeException("This store doesn't belong to this manager.");
        }
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
        response.sendRedirect("/restaurant/store/" +storeId + "/menu");
    }

    @GetMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public MenuResponseDto menuInfo (@RequestAttribute("cognitoUsername") String managerId,
                                     @PathVariable String storeId,
                                     @PathVariable String menuId) {

        Optional<Store> storeOptional = storeService.getStore(storeId);
        if(storeOptional.isEmpty()){
            throw new RuntimeException("store doesn't exist.");
        }
        Store store = storeOptional.get();
        if(store.getManagerId().equals(managerId)){
            throw new RuntimeException("This store doesn't belong to this manager.");
        }
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isPresent()){
            Menu menu = menuOptional.get();
            return new MenuResponseDto(menu);
        }
        throw new RuntimeException("Menu doesn't exist.");
    }

    @PutMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMenu(@RequestAttribute("cognitoUsername") String managerId,
                           @PathVariable String storeId,
                           @PathVariable String menuId,
                           @RequestBody MenuRequestDto data,
                           HttpServletResponse response) throws IOException {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if(storeOptional.isEmpty()){
            throw new RuntimeException("store doesn't exist.");
        }
        Store store = storeOptional.get();
        if(store.getManagerId().equals(managerId)){
            throw new RuntimeException("This store doesn't belong to this manager.");
        }
        menuService.updateMenu(menuId, data);
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Menu doesn't exist.");
        }
        Menu menu = menuOptional.get();
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageToUpdateMenu = sendingMessageConverter.createMessageToUpdateMenu(menuSqsDto);
        sqsService.sendToCustomer(messageToUpdateMenu);
        sqsService.sendToRider(messageToUpdateMenu);
        response.sendRedirect("/restaurant/store/" +storeId + "/menu");
    }

    @DeleteMapping("/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMenu(@RequestAttribute("cognitoUsername") String managerId,
                           @PathVariable String storeId,
                           @PathVariable String menuId,
                           HttpServletResponse response) throws IOException {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if(storeOptional.isEmpty()){
            throw new RuntimeException("store doesn't exist.");
        }
        Store store = storeOptional.get();
        if(store.getManagerId().equals(managerId)){
            throw new RuntimeException("This store doesn't belong to this manager.");
        }
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Menu doesn't exist.");
        }
        menuService.deleteMenu(menuId);
        String messageToDeleteMenu = sendingMessageConverter.createMessageToDeleteMenu(storeId, menuId);
        sqsService.sendToCustomer(messageToDeleteMenu);
        sqsService.sendToRider(messageToDeleteMenu);
        response.sendRedirect("/restaurant/store/" +storeId + "/menu");
    }

}
