package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Restaurant;
import msa.restaurant.DTO.ManagerForm;
import msa.restaurant.DTO.RestaurantForm;
import msa.restaurant.service.MemberService;
import msa.restaurant.service.RestaurantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/restaurant/member")
public class MemberController {

    private final MemberService memberService;
    private final RestaurantService restaurantService;

    public MemberController(MemberService memberService, RestaurantService restaurantService) {
        this.memberService = memberService;
        this.restaurantService = restaurantService;
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ManagerForm showMemberInfo(@RequestAttribute("cognitoUsername") String id){
        return memberService.getUserInfo(id);
    }

    @PutMapping("/info")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void updateMemberInfo(@RequestAttribute("cognitoUsername") String id,
                                 @RequestBody ManagerForm data,
                                 HttpServletResponse response) throws IOException {
        memberService.updateUserInfo(id, data);
        response.sendRedirect("/customer/member/info");
    }

    @GetMapping("/restaurants")
    @ResponseStatus(HttpStatus.OK)
    public List<Restaurant> restaurantList (@RequestAttribute("cognitoUsername") String id) {
        return memberService.getRestaurantList(id);
    }

    @GetMapping("/restaurants/add")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantAddForm () {
        return "restaurant enroll form";
    }

    @PostMapping("/restaurants/add")
    @ResponseStatus(HttpStatus.OK)
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
}
