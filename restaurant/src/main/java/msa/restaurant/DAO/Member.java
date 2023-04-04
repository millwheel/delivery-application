package msa.restaurant.DAO;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Member {
    @Id
    private Long id;
    private String username;
    private String password;
    private String email;
    private List stores;
}
