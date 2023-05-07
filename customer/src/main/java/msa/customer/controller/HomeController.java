package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.service.JoinService;
import msa.customer.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class HomeController {


    @GetMapping("/customer")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Customer server is activated successfully";
    }

    @GetMapping("customer/food-kind")
    @ResponseStatus(HttpStatus.OK)
    public String foodList() {
        // rendered by frontend
        return "Food kind list here";
    }

}
