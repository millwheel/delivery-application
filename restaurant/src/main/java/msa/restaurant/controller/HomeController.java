package msa.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class HomeController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Restaurant server is activated successfully";
    }

    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    public String orderList(){
        return "welcome page";
    }
}
