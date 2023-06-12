package msa.rider.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.rider.dto.RiderDto;
import msa.rider.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/rider/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public RiderDto showMemberInfo(@RequestAttribute("cognitoUsername") String id){
        return memberService.getUserInfo(id);
    }

    @PutMapping("/info")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void updateMemberInfo(@RequestAttribute("cognitoUsername") String id,
                                 @RequestBody RiderDto data,
                                 HttpServletResponse response) throws IOException {
        memberService.updateUserInfo(id, data);
        response.sendRedirect("/rider/member/info");
    }

}
