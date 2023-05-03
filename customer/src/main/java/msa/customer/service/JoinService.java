package msa.customer.service;

import msa.customer.DAO.Member;
import msa.customer.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JoinService {

    private final MemberRepository memberRepository;

    public JoinService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Boolean checkJoinedMember(String cognitoUsername){
        Optional<Member> user = memberRepository.findById(cognitoUsername);
        return user.isPresent();
    }

    public void joinMember(String cognitoUsername, String email, String phoneNumber){
        Member member = new Member();
        member.setMemberId(cognitoUsername);
        member.setEmail(email);
        member.setPhoneNumber(phoneNumber);
        memberRepository.make(member);
    }
}
