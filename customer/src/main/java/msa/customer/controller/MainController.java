package msa.customer.controller;

import msa.customer.entity.store.FoodKind;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/customer/main")
public class MainController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<FoodKind> foods() {
        return Arrays.stream(FoodKind.values()).toList();
    }
}
