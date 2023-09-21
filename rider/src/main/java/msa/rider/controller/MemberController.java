package msa.rider.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.rider.dto.rider.RiderRequestDto;
import msa.rider.dto.rider.RiderResponseDto;
import msa.rider.entity.member.Rider;
import msa.rider.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/rider/member")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RiderResponseDto showMemberInfo(@RequestAttribute("cognitoUsername") String riderId){
        Rider rider = memberService.getRider(riderId);
        return new RiderResponseDto(rider);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMemberInfo(@RequestAttribute("cognitoUsername") String riderId,
                                 @RequestBody RiderRequestDto data){
        memberService.updateRider(riderId, data);
    }

}
