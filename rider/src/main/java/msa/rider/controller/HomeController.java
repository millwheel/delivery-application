package msa.rider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/rider")
    public String home() {
        return "Rider server is activated successfully";
    }
}