package msa.customer.controller;

import msa.customer.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {

    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/customer")
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Customer server is activated successfully";
    }

    @GetMapping("/customer/main")
    @ResponseStatus(HttpStatus.OK)
    public String main(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt) {
        String cognitoUsername = memberService.getCognitoUsernameFromJwt(jwt);
        String email = memberService.getUserEmailFromJwt(jwt);

    }

}
