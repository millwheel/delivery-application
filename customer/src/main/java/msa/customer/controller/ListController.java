package msa.customer.controller;

import lombok.extern.slf4j.Slf4j;
import msa.customer.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ListController {

    private final MemberService memberService;

    public ListController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/rider/order-list")
    @ResponseStatus(HttpStatus.OK)
    public String orderList (@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt){

        return memberService.getUserEmail(jwt);
    }

}