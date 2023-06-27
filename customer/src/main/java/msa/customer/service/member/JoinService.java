package msa.customer.service.member;

import msa.customer.entity.member.Customer;
import msa.customer.repository.member.MemberRepository;
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
        return memberRepository.readMember(cognitoUsername).isPresent();
    }

    public void joinMember(String cognitoUsername, String email){
        Customer customer = new Customer();
        customer.setCustomerId(cognitoUsername);
        customer.setEmail(email);
        memberRepository.createMember(customer);
    }
}
