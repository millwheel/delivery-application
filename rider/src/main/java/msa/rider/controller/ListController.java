package msa.rider.controller;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.rider.service.MemberService;
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
    public String orderList (@RequestHeader Map<String, String> headers){

        if (headers == null){
            return "No Headers";
        }

        StringBuilder mapAsString = new StringBuilder("{");
        for (String key : headers.keySet()) {
            mapAsString.append(key + "=" + headers.get(key) + ", ");
        }
        mapAsString.delete(mapAsString.length()-2, mapAsString.length()).append("}");

        String headerString = mapAsString.toString();

        return headerString;

//        String token = jwt.get();
//        log.info("header={}", token);
//            Claims claims = memberService.parseJwtToken(token);
//            log.info("claims={}", claims);
//            String email = memberService.getEmailFromClaims(claims);
//            log.info("email={}", email);
//            return "Your token is " + token;
    }

}
