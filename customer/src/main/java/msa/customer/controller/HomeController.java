package msa.customer.controller;

import msa.customer.entity.store.FoodKind;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer") // (R) HomeController에서 url prefix가 customer로 되어있는 것이 조금 이상한 것 같습니다.
public class HomeController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home() {
        return "Customer server is activated successfully";
    } // (Q) 어떤 용도의 메서드인가요?

    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    public List<FoodKind> foods() {
        /*
            (C) 여기도 Stream API를 잘 사용해보시면 좋을 것 같습니다.
            단순히 음식 종류를 반환하는 것이라면, 아래와 같이 사용해보세요.
            (enum type을 return 하더라도 string으로 반환하는 것으로 알고 있는데, 한번 확인해주세요~!)

            + API에서 set을 반환하면 무작위 순서로 반환하기 때문에, 순서가 무관한 경우에만 set을 사용하는 것이 좋을 것 같습니다.
        */
        return Arrays.stream(FoodKind.values()).toList();
    }

}
