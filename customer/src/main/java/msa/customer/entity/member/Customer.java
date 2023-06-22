package msa.customer.entity.member;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Setter
@Getter
@NoArgsConstructor
//@DynamoDBTable(tableName = "delivery_customer")
@Document("customer")
public class Customer {

    @MongoId
    private String customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;

}