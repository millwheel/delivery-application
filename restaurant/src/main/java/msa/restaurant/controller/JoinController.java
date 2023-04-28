package msa.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DTO.JoinForm;
import msa.restaurant.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class JoinController {

    private final MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/restaurant/join")
    @ResponseStatus(HttpStatus.OK)
    public String joinForm(){
        return "join form: name, email, password, passwordConfirm";
    }

    @PostMapping("/restaurant/join")
    @ResponseStatus(HttpStatus.OK)
    public String join(@RequestBody JoinForm data){
        boolean result = memberService.join(data);
        if(!result){
            return "join failed. The email is used already or password doesn't match.";
        }
        log.info("join success");
        return "join succeeded";
    }

}
