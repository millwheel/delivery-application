package msa.restaurant.service;

import msa.restaurant.DAO.Manager;
import msa.restaurant.repository.member.MemberRepository;
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
        Optional<Manager> user = memberRepository.findById(cognitoUsername);
        return user.isPresent();
    }

    public void joinMember(String cognitoUsername, String email){
        Manager manager = new Manager();
        manager.setManagerId(cognitoUsername);
        manager.setEmail(email);
        memberRepository.create(manager);
    }
}
