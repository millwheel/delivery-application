package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class RestaurantController {
    private final MemberService memberService;

    public RestaurantController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/restaurant-list")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantList (@RequestAttribute("cognitoUsername") String id,
                                  HttpServletResponse response) throws IOException {
        Optional<String> address = memberService.getAddress(id);
        if(address.isEmpty()){
            response.sendRedirect("/customer/member/info");
        }
        return "restaurant";
    }
}
