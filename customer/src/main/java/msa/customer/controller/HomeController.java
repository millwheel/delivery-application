package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.service.JoinService;
import msa.customer.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class HomeController {

    private final ParseJwtService parseJwtService;
    private final JoinService joinService;

    public HomeController(ParseJwtService parseJwtService, JoinService joinService) {
        this.parseJwtService = parseJwtService;
        this.joinService = joinService;
    }

    @GetMapping("/customer")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Customer server is activated successfully";
    }

    @GetMapping("/customer/main-gate")
    @ResponseStatus(HttpStatus.OK)
    public void main(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                     @RequestParam(defaultValue = "/customer/food-kind") String redirectURL,
                     HttpServletResponse response) throws IOException {
        String cognitoUsername = parseJwtService.getCognitoUsernameFromJwt(jwt);
        if(!joinService.checkJoinedMember(cognitoUsername)){
            String email = parseJwtService.getEmailFromJwt(jwt);
            String phoneNumber = parseJwtService.getPhoneNumberFromJwt(jwt);
            joinService.joinMember(cognitoUsername, email, phoneNumber);
        }
        response.sendRedirect(redirectURL);
    }

    @GetMapping("customer/food-kind")
    @ResponseStatus(HttpStatus.OK)
    public String foodList() {
        // rendered by frontend
        return "Food kind list here";
    }

}
