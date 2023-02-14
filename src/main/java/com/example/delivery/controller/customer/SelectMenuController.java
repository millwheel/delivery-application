package com.example.delivery.controller.customer;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class SelectMenuController {

    @GetMapping("/restaurant/{id}")
    public String selectRestaurant(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id){
        return "menu list";
    }
}
