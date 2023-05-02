package msa.customer.controller;

import lombok.extern.slf4j.Slf4j;
import msa.customer.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/customer")
@RestController
public class ListController {

    private final MemberService memberService;

    public ListController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/order-list")
    @ResponseStatus(HttpStatus.OK)
    public String orderList (@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt){

        return memberService.getUserEmail(jwt);
    }

}