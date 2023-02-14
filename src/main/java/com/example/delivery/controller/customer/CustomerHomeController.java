package com.example.delivery.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerHomeController {

    @GetMapping("/customer")
    public String home (HttpServletRequest request, HttpServletResponse response){
        return "restaurant list";
    }
}
