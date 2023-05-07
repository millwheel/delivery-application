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
        log.info("set name={} for {}", name, id);
        memberRepository.setName(id, name);
    }

    public void setPhoneNumber(String id, String phoneNumber){
        log.info("set phoneNumber={} for {}", phoneNumber, id);
        memberRepository.setPhoneNumber(id, phoneNumber);
    }

    public void setAddress(String id, String address){
        log.info("set address={} for {}", address, id);
        memberRepository.setAddress(id, address);
    }

}
