package msa.rider.service;

import lombok.extern.slf4j.Slf4j;
import msa.rider.DAO.Member;
import msa.rider.DTO.JoinForm;
import msa.rider.repository.MemberRepository;
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
        member.setName(joinData.getName());
        member.setEmail(joinData.getEmail());
        if(!joinData.getPassword().equals(joinData.getPasswordConfirm())){
            log.info("Password doesn't match");
            return false;
        }
        member.setPassword(joinData.getPassword());
        memberRepository.make(member);
        return true;
    }

    public Member login(String email, String password){
        Optional<Member> user = memberRepository.findByEmail(email);
        if(user.isEmpty()){
            log.info("email doesn't exist");
            return null;
        }
        Member member = user.get();
        if(!password.equals(member.getPassword())){
            log.info("wrong password");
            return null;
        }
        return member;
    }

}
