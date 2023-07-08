package msa.rider.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.rider.dto.rider.RiderRequestDto;
import msa.rider.dto.rider.RiderResponseDto;
import msa.rider.entity.member.Rider;
import msa.rider.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/rider/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RiderResponseDto showMemberInfo(@RequestAttribute("cognitoUsername") String riderId){
        Rider rider = memberService.getRider(riderId).get();
        return new RiderResponseDto(rider);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMemberInfo(@RequestAttribute("cognitoUsername") String riderId,
                                 @RequestBody RiderRequestDto data,
                                 HttpServletResponse response) throws IOException {
        memberService.updateRider(riderId, data);
    }

}
