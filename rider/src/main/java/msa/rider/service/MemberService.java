package msa.rider.service;

import lombok.extern.slf4j.Slf4j;
import msa.rider.entity.Rider;
import msa.rider.dto.RiderDto;
import msa.rider.repository.member.MemberRepository;
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
        return memberRepository.findById(id).map(Rider::getName);
    }

    public Optional<String> getEmail(String id){
        return memberRepository.findById(id).map(Rider::getEmail);
    }

    public Optional<String> getPhoneNumber(String id){
        return memberRepository.findById(id).map(Rider::getPhoneNumber);
    }

    public Optional<String> getAddress(String id){
        return memberRepository.findById(id).map(Rider::getAddress);
    }

    public Optional<String> getAddressDetail(String id){
        return memberRepository.findById(id).map(Rider::getAddressDetail);
    }

    public Optional<Point> getCoordinates(String id){
        return memberRepository.findById(id).map(Rider::getCoordinates);
    }

    public RiderDto getUserInfo(String id){
        RiderDto riderDto = new RiderDto();
        getName(id).ifPresent(riderDto::setName);
        getEmail(id).ifPresent(riderDto::setEmail);
        getPhoneNumber(id).ifPresent(riderDto::setPhoneNumber);
        getAddress(id).ifPresent(riderDto::setAddress);
        getAddressDetail(id).ifPresent(riderDto::setAddressDetail);
        getCoordinates(id).ifPresent(riderDto::setLocation);
        return riderDto;
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

    public void updateUserInfo(String id, RiderDto data){
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
