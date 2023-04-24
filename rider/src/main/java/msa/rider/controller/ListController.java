package msa.rider.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.rider.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
public class ListController {

    private final MemberService memberService;

    public ListController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/rider/order-list")
    public String orderList (@RequestParam("id_token") Optional<String> jwt, HttpServletResponse response){

        if (jwt.isPresent()) {
            String token = jwt.get();
            log.info("header={}", token);
            Claims claims = memberService.parseJwtToken(token);
            log.info("claims={}", claims);
            return "Your JWT is " + token + " and order list is here";
        }
        return "No JWT";
    }

}
