package msa.restaurant.service;

import msa.restaurant.DAO.Member;
import msa.restaurant.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JoinService {

    private final MemberRepository memberRepository;

    @Autowired
    public JoinService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Boolean checkJoinedMember(String cognitoUsername){
        Optional<Member> user = memberRepository.findById(cognitoUsername);
        return user.isPresent();
    }

    public void joinMember(String cognitoUsername, String email){
        Member member = new Member();
        member.setMemberId(cognitoUsername);
        member.setEmail(email);
        memberRepository.make(member);
    }
}
