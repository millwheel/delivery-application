package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.manager.ManagerRequestDto;
import msa.restaurant.dto.manager.ManagerResponseDto;
import msa.restaurant.entity.member.Manager;
import msa.restaurant.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/restaurant/member")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ManagerResponseDto showMember(@RequestAttribute("cognitoUsername") String id){
        Manager manager = memberService.getManager(id).get();
        return new ManagerResponseDto(manager);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void updateMember(@RequestAttribute("cognitoUsername") String id,
                                 @RequestBody ManagerRequestDto data,
                                 HttpServletResponse response) throws IOException {
        memberService.updateManager(id, data);
        response.sendRedirect("/restaurant/member");
    }

}
