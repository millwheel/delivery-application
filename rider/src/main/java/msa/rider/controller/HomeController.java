package msa.rider.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class HomeController {

    @GetMapping("/rider")
    @ResponseStatus(HttpStatus.OK)
    public String home(@RequestParam("id_token") Optional<String> jwt){

        return "Rider server is activated successfully";
    }
}
