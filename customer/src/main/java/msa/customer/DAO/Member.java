package msa.customer.DAO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Setter
@Getter
@NoArgsConstructor
//@DynamoDBTable(tableName = "delivery_customer")
@Document("member")
public class Member {

    @MongoId
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private GeoJsonPoint coordinates;

}