package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.service.MemberService;
import msa.customer.service.RestaurantService;
import org.json.JSONObject;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class RestaurantController {
    private final MemberService memberService;
    private final RestaurantService restaurantService;

    public RestaurantController(MemberService memberService, RestaurantService restaurantService) {
        this.memberService = memberService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurant-list")
    @ResponseStatus(HttpStatus.OK)
    public JSONObject restaurantList (@RequestAttribute("cognitoUsername") String id,
                                      HttpServletResponse response) throws IOException {
        Optional<GeoJsonPoint> coordinates = memberService.getCoordinates(id);
        if(coordinates.isEmpty()){
            response.sendRedirect("/customer/member/info");
        }
        return restaurantService.showRestaurantListsNearCustomer(coordinates.get());
    }

    @GetMapping("/restaurant/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String showRestaurantInfo(@PathVariable String id){
        return "restaurant information" + id;
    }
}
