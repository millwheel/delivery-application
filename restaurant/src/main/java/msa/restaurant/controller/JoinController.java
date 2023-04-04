package msa.restaurant.controller;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.service.MemberService;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class JoinController {

    private final MemberService memberService;

    public JoinController(MemberService memberService) {
        this.memberService = memberService;
    }


}
