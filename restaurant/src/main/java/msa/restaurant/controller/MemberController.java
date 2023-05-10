package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DTO.MemberForm;
import msa.restaurant.service.MemberService;
import msa.restaurant.service.ParseJwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Slf4j
@RequestMapping("/restaurant/member")
public class MemberController {


    private final ParseJwtService parseJwtService;
    private final MemberService memberService;

    public MemberController(ParseJwtService parseJwtService, MemberService memberService) {
        this.parseJwtService = parseJwtService;
        this.memberService = memberService;
    }

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public MemberForm getMemberInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt){
        MemberForm member = new MemberForm();
        String id = parseJwtService.getCognitoUsernameFromJwt(jwt);
        memberService.getName(id).ifPresent(member::setName);
        memberService.getEmail(id).ifPresent(member::setEmail);
        memberService.getPhoneNumber(id).ifPresent(member::setPhoneNumber);
        memberService.getAddress(id).ifPresent(member::setAddress);
        memberService.getAddressDetail(id).ifPresent(member::setAddressDetail);
        return member;
    }

    @PutMapping("/info")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void setMemberInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String jwt,
                              @RequestBody MemberForm data,
                              HttpServletResponse response) throws IOException {
        String id = parseJwtService.getCognitoUsernameFromJwt(jwt);
        String name = data.getName();
        String phoneNumber = data.getPhoneNumber();
        String address = data.getAddress();
        String addressDetail = data.getAddressDetail();
        log.info(addressDetail);
        if(name != null) memberService.setName(id, name);
        if(phoneNumber != null) memberService.setPhoneNumber(id, phoneNumber);
        if(address != null) memberService.setAddress(id, address);
        if(addressDetail != null) memberService.setAddressDetail(id, addressDetail);
        response.sendRedirect("/restaurant/member/info");
    }
}
