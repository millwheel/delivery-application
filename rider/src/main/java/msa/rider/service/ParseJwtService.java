package msa.rider.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class ParseJwtService {

    public String getEmailFromJwt(String token){
        JSONObject payloadJson = parseJwt(token);
        return payloadJson.getString("email");
    }

    public String getCognitoUsernameFromJwt(String token){
        JSONObject payloadJson = parseJwt(token);
        return payloadJson.getString("cognito:username");
    }

    public JSONObject parseJwt(String token){
        String base64Payload = token.split("\\.")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Payload);
        String payloadString = new String(decodedBytes);
        return new JSONObject(payloadString);
    }
}
