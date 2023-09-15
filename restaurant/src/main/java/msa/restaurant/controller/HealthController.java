package msa.restaurant.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
public class HealthController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck(){
        return "Restaurant server is activated successfully";
    }

}
