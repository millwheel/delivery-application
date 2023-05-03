package msa.customer.controller;

import msa.customer.DTO.MemberForm;
import msa.customer.service.MemberService;
import msa.customer.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class MemberController {

    private final ParseJwtService parseJwtService;
    private final MemberService memberService;

    public MemberController(ParseJwtService parseJwtService, MemberService memberService) {
        this.parseJwtService = parseJwtService;
        this.memberService = memberService;
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
    @ResponseStatus(HttpStatus.OK)
    public MemberForm setMemberInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt, @RequestBody MemberForm data){
        MemberForm member = new MemberForm();
        String id = parseJwtService.getCognitoUsernameFromJwt(jwt);
        String name = data.getName();
        String address = data.getAddress();
        String phoneNumber = data.getPhoneNumber();
        if(name != null) {

        }
    }
}
