package msa.restaurant.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.menu.MenuPartInfoResponseDto;
import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.dto.menu.MenuResponseDto;
import msa.restaurant.dto.menu.MenuSqsDto;
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
    public List<MenuPartInfoResponseDto> menuList(@PathVariable String storeId){
        List<MenuPartInfo> menuPartInfoList = storeService.getMenuList(storeId);
        List<MenuPartInfoResponseDto> menuResponseDtoList = new ArrayList<>();
        menuPartInfoList.forEach(menuPartInfo -> {
            menuResponseDtoList.add(new MenuPartInfoResponseDto(menuPartInfo));
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
                        @PathVariable String storeId,
                        HttpServletResponse response) throws IOException {
        String menuId = menuService.createMenu(data);
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Cannot add menu into DB.");
        }
        Menu menu = menuOptional.get();
        MenuPartInfo menuPartInfo = new MenuPartInfo(menu);
        storeService.updateMenuList(storeId, menuPartInfo);
        MenuSqsDto menuSqsDto = new MenuSqsDto(menu);
        String messageForMenuInfo = messageConverter.createMessageToCreateMenu(menuSqsDto);
        SendMessageResult sendMessageResult = sqsService.sendToCustomer(messageForMenuInfo);
        log.info("message result={}", sendMessageResult);
        response.sendRedirect("/restaurant/{storeId}/menu/list");
    }

    @PutMapping("/update/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateMenu(@PathVariable String storeId,
                           @PathVariable String menuId,
                           HttpServletResponse response){

    }

    @DeleteMapping("/delete/{menuId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMenu(@PathVariable String storeId,
                           @PathVariable String menuId,
                           HttpServletResponse response) throws IOException {
        Optional<Menu> menuOptional = menuService.getMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("Cannot Delete Menu from DB. It doesn't exist.");
        }
        Menu menu = menuOptional.get();
        storeService.deleteMenuFromList(storeId, menuId);
        menuService.deleteMenu(menuId);
        String messageToDeleteMenu = messageConverter.createMessageToDeleteMenu(menuId);
        SendMessageResult sendMessageResult = sqsService.sendToCustomer(messageToDeleteMenu);
        log.info("message sending result={}", sendMessageResult);
        response.sendRedirect("/restaurant/menu/list");
    }

}
