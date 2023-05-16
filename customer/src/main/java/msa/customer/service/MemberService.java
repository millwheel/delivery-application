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

    public Optional<Coordinates> getCoordinates(String id){
        return memberRepository.findById(id).map(Member::getCoordinates);
    }

    public MemberForm getUserInfo(String id){
        MemberForm memberForm = new MemberForm();
        getName(id).ifPresent(memberForm::setName);
        getEmail(id).ifPresent(memberForm::setEmail);
        getPhoneNumber(id).ifPresent(memberForm::setPhoneNumber);
        getAddress(id).ifPresent(memberForm::setAddress);
        getAddressDetail(id).ifPresent(memberForm::setAddressDetail);
        getCoordinates(id).ifPresent(memberForm::setCoordinates);
        return memberForm;
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

    public void changeUserInfo(String id, MemberForm data){
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
