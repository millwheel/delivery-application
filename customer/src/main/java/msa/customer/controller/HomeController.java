package msa.customer.controller;

import msa.customer.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    private final ParseJwtService parseJwtService;

    public HomeController(ParseJwtService parseJwtService) {
        this.parseJwtService = parseJwtService;
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

        return "Main";
    }

}
