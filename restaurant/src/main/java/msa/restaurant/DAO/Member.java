package msa.restaurant.DAO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@Document("member")
public class Member {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private List<String> store;
    public Member(){

    }

}
