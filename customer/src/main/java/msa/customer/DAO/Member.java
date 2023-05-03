package msa.customer.DAO;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "delivery_customer")
public class Member {

    @DynamoDBHashKey
    private String memberId;
    @DynamoDBAttribute
    private String name;
    @DynamoDBAttribute
    private String email;
    @DynamoDBAttribute
    private String phoneNumber;
    @DynamoDBAttribute
    private String address;

}