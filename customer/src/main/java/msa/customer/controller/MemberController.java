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
    public CustomerResponseDto showMemberInfo(@RequestAttribute("cognitoUsername") String customerId){
        Customer customer = memberService.getCustomer(customerId);
        return new CustomerResponseDto(customer);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto updateMemberInfo(@RequestAttribute("cognitoUsername") String customerId,
                              @RequestBody CustomerRequestDto data) {
        Customer customer = memberService.updateCustomer(customerId, data);
        return new CustomerResponseDto(customer);
    }
}
