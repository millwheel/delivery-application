package msa.rider.DTO;

import lombok.Data;

@Data
public class UserSignInRequest {
    private String username;
    private String email;
    private String password;
    private String newPassword;
}
