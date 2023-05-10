package msa.restaurant.service;

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

    public String getPhoneNumberFromJwt(String token){
        JSONObject payloadJSON = parseJwt(token);
        return payloadJSON.getString("phone_number");
    }

    public JSONObject parseJwt(String token){
        String base64Payload = token.split("\\.")[1];
        byte[] decodedBytes = Base64.getDecoder().decode(base64Payload);
        String payloadString = new String(decodedBytes);
        return new JSONObject(payloadString);
    }
}
