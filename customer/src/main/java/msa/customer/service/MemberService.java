package msa.customer.service;

import lombok.extern.slf4j.Slf4j;
import msa.customer.DAO.Member;
import msa.customer.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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

    public void setName(String id, String name){
        memberRepository.setName(id, name);
    }

    public void setPhoneNumber(String id, String phoneNumber){
        memberRepository.setPhoneNumber(id, phoneNumber);
    }

    public void setAddress(String id, String address){
        memberRepository.setAddress(id, address);
    }

    public void setAddressDetail(String id, String addressDetail){
        memberRepository.setAddressDetail(id, addressDetail);
    }

}
