package msa.rider.DAO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Document("member")
public class Rider {

    @MongoId
    private String riderId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point coordinates;

}
