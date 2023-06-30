package msa.rider.entity.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Setter
@Getter
@NoArgsConstructor
@Document("rider")
public class Rider {
    @MongoId
    private String riderId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private Point location;
}
