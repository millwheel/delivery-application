package msa.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.entity.Menu;
import msa.restaurant.converter.MessageConverter;
import msa.restaurant.service.SqsService;
import msa.restaurant.service.StoreService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/restaurant/{restaurantId}/menu")
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
    public List<Menu> menuList(@PathVariable String restaurantId){
        return storeService.getMenuList(restaurantId).orElseGet(ArrayList::new);
    }
}
