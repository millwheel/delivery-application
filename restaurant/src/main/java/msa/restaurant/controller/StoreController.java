package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Store;
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
@RequestMapping("/restaurant/store")
public class StoreController {

    private final RestaurantService restaurantService;
    private final MemberService memberService;

    public StoreController(RestaurantService restaurantService, MemberService memberService) {
        this.restaurantService = restaurantService;
        this.memberService = memberService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Store> restaurantList (@RequestAttribute("cognitoUsername") String managerId) {
        return memberService.getRestaurantList(managerId);
    }

    @GetMapping("/add")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantAddForm () {
        return "restaurant enroll form";
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void restaurantAdd (@RequestAttribute("cognitoUsername") String managerId,
                               @RequestBody RestaurantForm data,
                               HttpServletResponse response) throws IOException {
        String restaurantId = restaurantService.createRestaurantInfo(data);
        Store store = restaurantService.getRestaurant(restaurantId).get();
        List<Store> storeList = memberService.getRestaurantList(managerId);
        storeList.add(store);
        memberService.setRestaurantList(managerId, storeList);
        response.sendRedirect("/manager/store/list");
    }

    @GetMapping("/update/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantUpdateForm(@PathVariable String restaurantId){
        return "restaurant info update form here.";
    }

    @PutMapping("/update/{restaurantId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void restaurantUpdate(@RequestAttribute("cognitoUsername") String managerId,
                                 @PathVariable String restaurantId,
                                 @RequestBody RestaurantForm data,
                                 HttpServletResponse response) throws IOException {

        Optional<Store> restaurant = restaurantService.getRestaurant(restaurantId);
        if (restaurant.isEmpty()){
            response.sendRedirect("/manager/restaurant/update/error");
        }
        restaurantService.updateRestaurantInfo(restaurantId, data);
        response.sendRedirect("");
    }

    @GetMapping("/update/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String restaurantUpdateError(){
        return "Wrong restaurant id";
    }
}

