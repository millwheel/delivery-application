package msa.rider.service.member;

import msa.rider.entity.member.Rider;
import msa.rider.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private final MemberRepository memberRepository;

    @Autowired
    public JoinService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Boolean checkJoinedMember(String cognitoUsername){
        return memberRepository.findById(cognitoUsername).isPresent();
    }

    public void joinMember(String cognitoUsername, String email){
        Rider customer = new Rider();
        customer.setRiderId(cognitoUsername);
        customer.setEmail(email);
        memberRepository.create(customer);
    }
}
