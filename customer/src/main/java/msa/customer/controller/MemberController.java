package msa.customer.controller;

import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.dto.customer.CustomerResponseDto;
import msa.customer.entity.member.Customer;
import msa.customer.service.member.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer/member")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto showMemberInfo(@RequestAttribute("cognitoUsername") String riderId){
        Customer customer = memberService.getCustomer(riderId);
        return new CustomerResponseDto(customer);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateMemberInfo(@RequestAttribute("cognitoUsername") String riderId,
                              @RequestBody CustomerRequestDto data) {
        memberService.updateCustomer(riderId, data);
    }
}
