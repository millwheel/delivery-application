package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.DTO.MemberForm;
import msa.customer.service.JoinService;
import msa.customer.service.MemberService;
import msa.customer.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/customer")
public class MemberController {

    private final ParseJwtService parseJwtService;
    private final MemberService memberService;
    private final JoinService joinService;

    public MemberController(ParseJwtService parseJwtService, MemberService memberService, JoinService joinService) {
        this.parseJwtService = parseJwtService;
        this.memberService = memberService;
        this.joinService = joinService;
    }

    @GetMapping("/main-gate")
    @ResponseStatus(HttpStatus.OK)
    public String main(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                     @RequestParam(defaultValue = "/customer/food-kind") String redirectURL,
                     HttpServletResponse response) throws IOException {
        String cognitoUsername = parseJwtService.getCognitoUsernameFromJwt(jwt);
        if(!joinService.checkJoinedMember(cognitoUsername)){
            String email = parseJwtService.getEmailFromJwt(jwt);
            joinService.joinMember(cognitoUsername, email);
        }
        return "member main-gate";
    }

    @GetMapping("/member/info")
    @ResponseStatus(HttpStatus.OK)
    public MemberForm getMemberInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt){
        MemberForm member = new MemberForm();
        String id = parseJwtService.getCognitoUsernameFromJwt(jwt);
        memberService.getName(id).ifPresent(member::setName);
        memberService.getEmail(id).ifPresent(member::setEmail);
        memberService.getPhoneNumber(id).ifPresent(member::setPhoneNumber);
        memberService.getAddress(id).ifPresent(member::setAddress);
        return member;
    }

    @PutMapping("/member/info")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void setMemberInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                              @RequestBody MemberForm data,
                              HttpServletResponse response) throws IOException {
        String id = parseJwtService.getCognitoUsernameFromJwt(jwt);
        String name = data.getName();
        String address = data.getAddress();
        String phoneNumber = data.getPhoneNumber();
        if(name != null) memberService.setName(id, name);
        if(phoneNumber != null) memberService.setPhoneNumber(id, phoneNumber);
        if(address != null) memberService.setAddress(id, address);
        response.sendRedirect("/customer/main-gate");
    }
}
