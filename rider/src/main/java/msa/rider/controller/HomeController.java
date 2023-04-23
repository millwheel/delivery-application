package msa.rider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/rider")
    public String home(@RequestParam(required = false) String jwt){
        if (jwt != null){
            return "Your JWT is " + jwt;
        }
        return "Rider server is activated successfully";
    }
}
