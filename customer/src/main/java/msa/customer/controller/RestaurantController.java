package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.customer.DAO.FoodKindType;
import msa.customer.DAO.Restaurant;
import msa.customer.service.MemberService;
import msa.customer.service.RestaurantService;
import org.json.JSONArray;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/customer/food/{kind}")
public class RestaurantController {
    private final MemberService memberService;
    private final RestaurantService restaurantService;

    public RestaurantController(MemberService memberService, RestaurantService restaurantService) {
        this.memberService = memberService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurant-list")
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> restaurantList (@RequestAttribute("cognitoUsername") String id,
                                            @PathVariable FoodKindType kind,
                                            HttpServletResponse response) throws IOException {
        Optional<Point> coordinates = memberService.getCoordinates(id);
        if(coordinates.isEmpty()){
            response.sendRedirect("/customer/member/info");
        }
        log.info("coordinates={}", coordinates);
        log.info("food kind={}", kind);
        return restaurantService.showRestaurantListsNearCustomer(coordinates.get(), kind);
    }

}
