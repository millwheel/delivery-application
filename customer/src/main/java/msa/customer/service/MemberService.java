package msa.customer.service;

import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import msa.customer.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressService addressService;
    @Autowired
    public MemberService(MemberRepository memberRepository, AddressService addressService) {
        this.memberRepository = memberRepository;
        this.addressService = addressService;
    }


    public Optional<Customer> getCustomer(String customerId){
        return memberRepository.findById(customerId);
    }

    public Optional<Point> getCoordinates(String customerId) {
        return memberRepository.findById(customerId).map(Customer::getLocation);
    }

    public void updateCustomer(String customerId, CustomerRequestDto data){
        if (data.getAddress() == null) {
            memberRepository.update(customerId, data);
        } else {
            Point coordinate = addressService.getCoordinate(data.getAddress());
            memberRepository.update(customerId, data, coordinate);
        }
    }

}
