package msa.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class HomeController {

    @GetMapping("/customer")
    public String home(){
        return "Customer server is activated successfully";
    }

}
