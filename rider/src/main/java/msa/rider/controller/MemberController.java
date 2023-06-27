package msa.rider.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.rider.dto.rider.RiderResponseDto;
import msa.rider.entity.Rider;
import msa.rider.service.MemberService;
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
        Optional<Rider> riderOptional = memberService.getRider(riderId);
        if (riderOptional.isEmpty()){
            throw new RuntimeException("rider doesn't exist.");
        }
        Rider rider = riderOptional.get();
        return new RiderResponseDto(rider);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void updateMemberInfo(@RequestAttribute("cognitoUsername") String id,
                                 @RequestBody RiderResponseDto data,
                                 HttpServletResponse response) throws IOException {
        memberService.updateRider(id, data);
        response.sendRedirect("/rider/member");
    }

}
