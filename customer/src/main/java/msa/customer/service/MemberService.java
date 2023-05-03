package msa.customer.service;

import lombok.extern.slf4j.Slf4j;
import msa.customer.DAO.Member;
import msa.customer.DTO.JoinForm;
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

    public String getAddress(String id){
        memberRepository.findById(id).ifPresent(member -> {
            return member.getAddress();
        });
        return null;
    }

}
