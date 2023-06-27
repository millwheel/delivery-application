package msa.rider.controller;

import msa.rider.dto.rider.RiderAddressRequestDto;
import msa.rider.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rider")
@RestController
public class HomeController {

    private final MemberService memberService;

    public HomeController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String home(){
        return "Rider server is activated successfully";
    }

    @PatchMapping("/ready")
    @ResponseStatus(HttpStatus.OK)
    public void updateAddress(@RequestAttribute("cognitoUsername") String riderId,
                              @RequestBody RiderAddressRequestDto data){
        memberService.updateAddress(riderId, data);
    }
}