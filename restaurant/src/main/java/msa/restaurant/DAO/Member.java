package msa.restaurant.DAO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Member {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<String> store;
    public Member(){

    }

}
