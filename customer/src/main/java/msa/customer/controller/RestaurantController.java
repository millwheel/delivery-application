package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import msa.customer.service.MemberService;
import msa.customer.service.ParseJwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class RestaurantController {

    private final ParseJwtService parseJwtService;
    private final MemberService memberService;

    @GetMapping("/restaurant-list")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantList (@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                                  HttpServletResponse response) throws IOException {
        String id = parseJwtService.getCognitoUsernameFromJwt(jwt);
        Optional<String> address = memberService.getAddress(id);
        if(address.isEmpty()){
            response.sendRedirect("/customer/member/info");
        }
        return "restaurant";
    }
}
