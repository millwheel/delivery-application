package msa.rider.service;

import lombok.extern.slf4j.Slf4j;
import msa.rider.DAO.Member;
import msa.rider.DTO.JoinForm;
import msa.rider.repository.MemberRepository;
import org.bson.json.JsonObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Slf4j
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    public String parseJwtPayload(String token){
        String base64Payload = token.split("\\.")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Payload);
        return new String(decodedBytes);
    }

    public String getEmailFromPayload(String payloadString){
        JSONObject payloadJson = new JSONObject(payloadString);
        return payloadJson.getString("email");
    }



}
