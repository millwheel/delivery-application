package msa.customer.DAO;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
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
//@DynamoDBTable(tableName = "delivery_customer")
@Document("member")
public class Member {

    @MongoId
    private String memberId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;

}