package msa.rider.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.rider.service.MemberService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

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

        return jwt;
    }

}
