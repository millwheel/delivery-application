package msa.rider.controller;

import lombok.extern.slf4j.Slf4j;
import msa.rider.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
        String decodedPayloadString = memberService.parseJwtPayload(jwt);
        return memberService.getEmailFromPayload(decodedPayloadString);
    }

}
