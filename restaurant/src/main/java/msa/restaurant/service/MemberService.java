package msa.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Manager;
import msa.restaurant.DTO.MemberForm;
import msa.restaurant.repository.member.MemberRepository;
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
        return memberRepository.findById(id).map(Manager::getName);
    }

    public Optional<String> getEmail(String id){
        return memberRepository.findById(id).map(Manager::getEmail);
    }

    public Optional<String> getPhoneNumber(String id){
        return memberRepository.findById(id).map(Manager::getPhoneNumber);
    }


    public MemberForm getUserInfo(String id){
        MemberForm memberForm = new MemberForm();
        getName(id).ifPresent(memberForm::setName);
        getEmail(id).ifPresent(memberForm::setEmail);
        getPhoneNumber(id).ifPresent(memberForm::setPhoneNumber);
        return memberForm;
    }

    public void setName(String id, String name){
        memberRepository.setName(id, name);
    }

    public void setPhoneNumber(String id, String phoneNumber){
        memberRepository.setPhoneNumber(id, phoneNumber);
    }


    public void updateUserInfo(String id, MemberForm data){
        String name = data.getName();
        String phoneNumber = data.getPhoneNumber();
        String address = data.getAddress();
        String addressDetail = data.getAddressDetail();
        if(name != null) setName(id, name);
        if(phoneNumber != null) setPhoneNumber(id, phoneNumber);
    }

}
