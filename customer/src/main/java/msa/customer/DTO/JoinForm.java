package msa.customer.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinForm {
    private String id;
    private String name;
    private String email;
    private String password;
    private String passwordConfirm;
}
