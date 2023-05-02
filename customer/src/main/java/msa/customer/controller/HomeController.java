package msa.customer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/customer")
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "Customer server is activated successfully";
    }

}
