package msa.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/restaurant/{restaurantId}")
public class MenuController {

    @GetMapping("/menu-list")
    @ResponseStatus(HttpStatus.OK)
    public String menuList (){

        return "menu";
    }
}
