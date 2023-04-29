package msa.rider.controller;

import lombok.extern.slf4j.Slf4j;
import msa.rider.DTO.JoinForm;
import msa.rider.service.MemberService;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class JoinController {

    private final MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/join")
    public String joinForm(){
        return "join form: name, email, password, passwordConfirm";
    }

    @PostMapping("/join")
    public String join(@RequestBody JoinForm data){
        boolean result = memberService.join(data);
        if(!result){
            return "join failed. The email is used already or password doesn't match.";
        }
        log.info("join success");
        return "join succeeded";
    }

}
