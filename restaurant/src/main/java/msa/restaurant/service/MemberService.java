package msa.restaurant.service;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Member;
import msa.restaurant.DTO.JoinForm;
import msa.restaurant.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
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

    public String makeJwtToken(){
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // (1)
                .setIssuer("fresh") // (2)
                .setIssuedAt(now) // (3)
                .setExpiration(new Date(now.getTime() + Duration.ofMinutes(30).toMillis())) // (4)
                .claim("id", "아이디") // (5)
                .claim("email", "ajufresh@gmail.com")
                .signWith(SignatureAlgorithm.HS256, "sizeOfSecretKeyHasToBeMoreThan256BitsSoMakeItLonger") // (6)
                .compact();
    }

}
