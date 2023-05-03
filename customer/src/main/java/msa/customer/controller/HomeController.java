package msa.customer.controller;

import msa.customer.service.JoinService;
import msa.customer.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/customer/main")
    @ResponseStatus(HttpStatus.OK)
    public String main(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        String cognitoUsername = parseJwtService.getCognitoUsernameFromJwt(jwt);
        if(!joinService.checkJoinedMember(cognitoUsername)){
            String email = parseJwtService.getEmailFromJwt(jwt);
            String phoneNumber = parseJwtService.getPhoneNumberFromJwt(jwt);
            joinService.joinMember(cognitoUsername, email, phoneNumber);
        }
        return "Main";
    }

}
