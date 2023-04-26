package msa.rider.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.rider.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
public class ListController {

    private final MemberService memberService;

    public ListController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/order-list")
    @ResponseStatus(HttpStatus.OK)
    public String orderList (@RequestHeader(HttpHeaders.AUTHORIZATION) Optional<String> jwt, HttpServletResponse response){

        if (jwt.isPresent()) {
            String token = jwt.get();
            log.info("header={}", token);
//            Claims claims = memberService.parseJwtToken(token);
//            log.info("claims={}", claims);
//            String email = memberService.getEmailFromClaims(claims);
//            log.info("email={}", email);
            return "Your token is " + token;
        }
        return "No JWT";
    }

}
