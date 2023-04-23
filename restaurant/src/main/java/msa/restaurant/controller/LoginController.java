package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Member;
import msa.restaurant.DTO.LoginForm;
import msa.restaurant.service.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class LoginController {

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/restaurant/login")
    public String joinForm(){
        return "login form: email, password";
    }

    @PostMapping("/restaurant/login")
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
        response.sendRedirect("/");

    }
}
