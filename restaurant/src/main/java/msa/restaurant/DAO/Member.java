package msa.restaurant.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document("member")
public class Member {

    @MongoId
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Coordinates coordinates;

}
