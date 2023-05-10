package msa.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
