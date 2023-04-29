package msa.rider.DAO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document("member")
public class Member {

    private String name;
    private String email;
    private String password;
    public Member(){

    }

}
