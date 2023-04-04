package msa.rider.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinForm {
    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
}
