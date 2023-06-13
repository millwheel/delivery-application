package msa.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.MenuResponseDto;
import msa.restaurant.entity.Menu;
import msa.restaurant.converter.MessageConverter;
import msa.restaurant.service.SqsService;
import msa.restaurant.service.StoreService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/restaurant/{storeId}/menu")
public class MenuController {

    private final StoreService storeService;
    private final MessageConverter messageConverter;
    private final SqsService sqsService;

    public MenuController(StoreService storeService, MessageConverter messageConverter, SqsService sqsService) {
        this.storeService = storeService;
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
}
