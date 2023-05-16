package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.customer.DTO.MemberForm;
import msa.customer.service.MemberService;
import msa.customer.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/info")
    @ResponseStatus(HttpStatus.OK)
    public MemberForm getMemberInfo(@RequestAttribute("cognitoUsername") String id){
        return memberService.getUserInfo(id);
    }

    @PutMapping("/member/info")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void setMemberInfo(@RequestAttribute("cognitoUsername") String id,
                              @RequestBody MemberForm data,
                              HttpServletResponse response) throws IOException {
        memberService.changeUserInfo(id, data);
        response.sendRedirect("/customer/member/info");
    }
}
