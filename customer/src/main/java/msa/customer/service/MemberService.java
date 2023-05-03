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

    public Boolean join(JoinForm joinData){
        Member member = new Member();
        if(memberRepository.findByEmail(joinData.getEmail()).isPresent()){
            log.info("this email is already used");
            return false;
        }
        member.setMemberId(joinData.getId());
        member.setName(joinData.getName());
        member.setEmail(joinData.getEmail());
        if(!joinData.getPassword().equals(joinData.getPasswordConfirm())){
            log.info("Password doesn't match");
            return false;
        }
        memberRepository.make(member);
        return true;
    }

    public Member login(String id, String password){
        Optional<Member> user = memberRepository.findById(id);
        if(user.isEmpty()){
            log.info("email doesn't exist");
            return null;
        }
        Member member = user.get();
        return member;
    }

}
