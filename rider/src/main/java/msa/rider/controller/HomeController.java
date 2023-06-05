package msa.rider.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/rider")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Rider server is activated successfully";
    }
}