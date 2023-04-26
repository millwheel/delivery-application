package msa.rider.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.rider.DAO.Member;
import msa.rider.DTO.LoginForm;
import msa.rider.service.MemberService;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/rider/login")
    @ResponseStatus(HttpStatus.OK)
    public String joinForm(){
        return "login form: email, password";
    }

    @PostMapping("/rider/login")
    @ResponseStatus(HttpStatus.OK)
    public void login(@RequestBody LoginForm data,
                      HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        Member loginMember = memberService.login(data.getEmail(), data.getPassword());

        if(loginMember == null){
            log.info("member doesn't exist.");
            response.sendRedirect("/login");
            return;
        }
        log.info("login success");

        String id = loginMember.getId();
        String email = loginMember.getEmail();

        String token = memberService.makeJwtToken(id, email);
        log.info("JWT={}", token);
        response.setHeader(HttpHeaders.AUTHORIZATION, token);
        String header = response.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("AUTHORIZATION={}", header);
        response.sendRedirect("/rider/order-list");
    }
}
