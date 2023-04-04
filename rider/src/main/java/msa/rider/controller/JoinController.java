package msa.rider.controller;

import lombok.extern.slf4j.Slf4j;
import msa.rider.DTO.JoinForm;
import msa.rider.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JoinController {

    private final MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("join")
    public String join(@RequestBody JoinForm data){
        boolean result = memberService.join(data);
        if(!result){
            return "join failed. The email is used already or password doesn't match.";
        }
        log.info("join success");
        return "join succeeded";
    }

}
