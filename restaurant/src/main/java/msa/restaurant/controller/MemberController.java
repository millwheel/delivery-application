package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DTO.ManagerForm;
import msa.restaurant.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/manager/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public ManagerForm showMemberInfo(@RequestAttribute("cognitoUsername") String id){
        return memberService.getUserInfo(id);
    }

    @PutMapping("/info")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void updateMemberInfo(@RequestAttribute("cognitoUsername") String id,
                                 @RequestBody ManagerForm data,
                                 HttpServletResponse response) throws IOException {
        memberService.updateUserInfo(id, data);
        response.sendRedirect("/customer/member/info");
    }

}
