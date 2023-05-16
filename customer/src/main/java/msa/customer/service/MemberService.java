package msa.customer.service;

import lombok.extern.slf4j.Slf4j;
import msa.customer.DAO.Coordinates;
import msa.customer.DAO.Member;
import msa.customer.DTO.MemberForm;
import msa.customer.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        return memberRepository.findById(id).map(Member::getName);
    }

    public Optional<String> getEmail(String id){
        return memberRepository.findById(id).map(Member::getEmail);
    }

    public Optional<String> getPhoneNumber(String id){
        return memberRepository.findById(id).map(Member::getPhoneNumber);
    }

    public Optional<String> getAddress(String id){
        return memberRepository.findById(id).map(Member::getAddress);
    }

    public Optional<String> getAddressDetail(String id){
        return memberRepository.findById(id).map(Member::getAddressDetail);
    }

    public MemberForm getUserInfo(String id){
        MemberForm member = new MemberForm();
        getName(id).ifPresent(member::setName);
        getEmail(id).ifPresent(member::setEmail);
        getPhoneNumber(id).ifPresent(member::setPhoneNumber);
        getAddress(id).ifPresent(member::setAddress);
        getAddressDetail(id).ifPresent(member::setAddressDetail);
        return member;
    }

    public void setName(String id, String name){
        memberRepository.setName(id, name);
    }

    public void setPhoneNumber(String id, String phoneNumber){
        memberRepository.setPhoneNumber(id, phoneNumber);
    }

    public void setAddress(String id, String address){
        memberRepository.setAddress(id, address);
        Coordinates coordinate = addressService.getCoordinate(address);
        memberRepository.setCoordinates(id, coordinate);
    }

    public void setAddressDetail(String id, String addressDetail){
        memberRepository.setAddressDetail(id, addressDetail);
    }

}
