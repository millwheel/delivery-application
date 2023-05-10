package msa.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/restaurant/menu")
public class MenuController {

    @GetMapping("/list")
    public String menuList(){
        return "menu list";
    }
}
