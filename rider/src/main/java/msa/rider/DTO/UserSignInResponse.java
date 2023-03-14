package msa.rider.DTO;

import lombok.Data;

@Data
public class UserSignInResponse {
    private String accessToken;
    private String idToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
}
