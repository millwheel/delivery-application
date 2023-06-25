package msa.customer.controller;

import msa.customer.entity.store.FoodKind;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class HomeController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Customer server is activated successfully";
    }

    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    public List<String> foods() {
        List<String> foodKindList = new ArrayList<>();
        for (FoodKind foodKind : EnumSet.allOf(FoodKind.class)){
            foodKindList.add(foodKind.toString());
        }
        return foodKindList;
    }

}
