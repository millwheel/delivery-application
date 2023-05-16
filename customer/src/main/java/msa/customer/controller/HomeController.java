package msa.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {


    @GetMapping("/customer")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Customer server is activated successfully";
    }

    @GetMapping("/customer/main")
    @ResponseStatus(HttpStatus.OK)
    public String foodList() {
        // rendered by frontend
        return "Food kind list";
    }

}
