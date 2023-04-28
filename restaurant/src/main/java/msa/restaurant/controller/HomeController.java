package msa.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/restaurant")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Restaurant server is activated successfully";
    }
}
