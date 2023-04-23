package msa.rider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HomeController {

    @GetMapping("/rider")
    public String home(@RequestParam("id_token") Optional<String> jwt){
        if (jwt.isPresent()){
            String token = jwt.get();
            return "Your JWT is " + token;
        }
        return "Rider server is activated successfully";
    }
}
