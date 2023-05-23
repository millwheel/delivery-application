package msa.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class HomeController {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Customer server is activated successfully";
    }

    @GetMapping("/foods")
    @ResponseStatus(HttpStatus.OK)
    public String foodsKind() {
        // rendered by frontend
        return "Food kind list";
    }

}
