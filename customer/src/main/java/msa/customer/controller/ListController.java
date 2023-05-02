package msa.customer.controller;

import lombok.extern.slf4j.Slf4j;
import msa.customer.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/customer")
@RestController
public class ListController {

    private final ParseJwtService parseJwtService;

    public ListController(ParseJwtService parseJwtService) {
        this.parseJwtService = parseJwtService;
    }

    @GetMapping("/menu-list")
    @ResponseStatus(HttpStatus.OK)
    public String orderList (@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt){
        String cognitoUsername = parseJwtService.getCognitoUsernameFromJwt(jwt);
        String email = parseJwtService.getUserEmailFromJwt(jwt);
        return cognitoUsername + " " + email;
    }

}