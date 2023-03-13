package com.example.delivery.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class CustomerLoginController {

    @GetMapping("/login")
    public String login (HttpServletRequest request, HttpServletResponse response){
        return "login";
    }

    @GetMapping("/logout")
    public String logout (HttpServletRequest request, HttpServletResponse response){
        return "logout";
    }

    @GetMapping("/join")
    public String join (HttpServletRequest request, HttpServletResponse response){
        return "join";
    }
}
