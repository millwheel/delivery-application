package msa.customer.service;

import lombok.extern.slf4j.Slf4j;
import msa.customer.entity.Customer;
import msa.customer.dto.CustomerDto;
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

    public Optional<String> getName(String id){
        return memberRepository.findById(id).map(Customer::getName);
    }

    public Optional<String> getEmail(String id){
        return memberRepository.findById(id).map(Customer::getEmail);
    }

    public Optional<String> getPhoneNumber(String id){
        return memberRepository.findById(id).map(Customer::getPhoneNumber);
    }

    public Optional<String> getAddress(String id){
        return memberRepository.findById(id).map(Customer::getAddress);
    }

    public Optional<String> getAddressDetail(String id){
        return memberRepository.findById(id).map(Customer::getAddressDetail);
    }

    public Optional<Point> getCoordinates(String id){
        return memberRepository.findById(id).map(Customer::getCoordinates);
    }

    public CustomerDto getUserInfo(String id){
        CustomerDto customerDto = new CustomerDto();
        getName(id).ifPresent(customerDto::setName);
        getEmail(id).ifPresent(customerDto::setEmail);
        getPhoneNumber(id).ifPresent(customerDto::setPhoneNumber);
        getAddress(id).ifPresent(customerDto::setAddress);
        getAddressDetail(id).ifPresent(customerDto::setAddressDetail);
        getCoordinates(id).ifPresent(customerDto::setLocation);
        return customerDto;
    }

    public void setName(String id, String name){
        memberRepository.setName(id, name);
    }

    public void setPhoneNumber(String id, String phoneNumber){
        memberRepository.setPhoneNumber(id, phoneNumber);
    }

    public void setAddress(String id, String address){
        memberRepository.setAddress(id, address);
        Point coordinate = addressService.getCoordinate(address);
        memberRepository.setCoordinates(id, coordinate);
    }

    public void setAddressDetail(String id, String addressDetail){
        memberRepository.setAddressDetail(id, addressDetail);
    }

    public void updateUserInfo(String id, CustomerDto data){
        String name = data.getName();
        String phoneNumber = data.getPhoneNumber();
        String address = data.getAddress();
        String addressDetail = data.getAddressDetail();
        if(name != null) setName(id, name);
        if(phoneNumber != null) setPhoneNumber(id, phoneNumber);
        if(address != null) setAddress(id, address);
        if(addressDetail != null) setAddressDetail(id, addressDetail);
    }

}
