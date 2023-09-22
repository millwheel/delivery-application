package msa.customer.service.member;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import msa.customer.repository.member.MemberRepository;
import msa.customer.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AddressService addressService;

    public Customer getCustomer(String customerId){
        return memberRepository.readCustomer(customerId);
    }

    public Point getLocation(String customerId) {
        return memberRepository.readCustomer(customerId).getLocation();
    }

    public Customer updateCustomer(String customerId, CustomerRequestDto data){
        if (data.getAddress() == null) {
            return memberRepository.updateCustomer(customerId, data);
        } else {
            Point coordinate = addressService.getCoordinate(data.getAddress());
            return memberRepository.updateCustomer(customerId, data, coordinate);
        }
    }

}
