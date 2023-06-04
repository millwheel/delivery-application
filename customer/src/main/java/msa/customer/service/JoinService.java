package msa.customer.service;

import msa.customer.DAO.Customer;
import msa.customer.repository.member.MemberRepository;
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
        Optional<Customer> user = memberRepository.findById(cognitoUsername);
        return user.isPresent();
    }

    public void joinMember(String cognitoUsername, String email){
        Customer customer = new Customer();
        customer.setCustomerId(cognitoUsername);
        customer.setEmail(email);
        memberRepository.make(customer);
    }
}
