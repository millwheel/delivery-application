package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.customer.entity.FoodKindType;
import msa.customer.entity.Store;
import msa.customer.service.MemberService;
import msa.customer.service.StoreService;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/customer/{foodKind}")
public class RestaurantController {
    private final MemberService memberService;
    private final StoreService storeService;

    public RestaurantController(MemberService memberService, StoreService storeService) {
        this.memberService = memberService;
        this.storeService = storeService;
    }

    @GetMapping("/restaurant-list")
    @ResponseStatus(HttpStatus.OK)
    public List<Store> restaurantList (@RequestAttribute("cognitoUsername") String customerId,
                                       @PathVariable FoodKindType foodKind,
                                       HttpServletResponse response) throws IOException {
        Optional<Point> coordinates = memberService.getCoordinates(customerId);
        if(coordinates.isEmpty()){
            response.sendRedirect("/customer/member/info");
        }
        return storeService.showStoreListsNearCustomer(coordinates.get(), foodKind);
    }

}
