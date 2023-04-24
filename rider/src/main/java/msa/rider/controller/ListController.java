package msa.rider.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class ListController {

    @GetMapping("/rider/order-list")
    public String orderList (@RequestParam ("id_token") Optional<String> jwt, HttpServletResponse response){
        if (jwt.isPresent()) {
            String token = jwt.get();
            return "Your JWT is " + token + " and order list is here";
        }
        return "No JWT";
    }

}
