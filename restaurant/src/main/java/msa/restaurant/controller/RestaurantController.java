package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Restaurant;
import msa.restaurant.DTO.RestaurantForm;
import msa.restaurant.service.MemberService;
import msa.restaurant.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/manager/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final MemberService memberService;

    public RestaurantController(RestaurantService restaurantService, MemberService memberService) {
        this.restaurantService = restaurantService;
        this.memberService = memberService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> restaurantList (@RequestAttribute("cognitoUsername") String id) {
        return memberService.getRestaurantList(id);
    }

    @GetMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantAddForm () {
        return "restaurant enroll form";
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void restaurantAdd (@RequestAttribute("cognitoUsername") String id,
                               @RequestBody RestaurantForm data,
                               HttpServletResponse response) throws IOException {
        String restaurantId = restaurantService.createRestaurantInfo(data);
        Restaurant restaurant = restaurantService.getRestaurant(id).get();
        List<Restaurant> restaurantList = memberService.getRestaurantList(id);
        restaurantList.add(restaurant);
        memberService.setRestaurantList(id, restaurantList);
        response.sendRedirect("/restaurant/member/restaurants");
    }

    @GetMapping("/update/{number}")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantUpdateForm(@PathVariable String number){
        return "restaurant info update form here.";
    }

    @PutMapping("/update/{number}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void restaurantUpdate(@RequestAttribute("cognitoUsername") String id,
                                 @PathVariable String number,
                                 @RequestBody RestaurantForm data,
                                 HttpServletResponse response) throws IOException {

        response.sendRedirect("/restaurant/member/restaurants");
    }
}

