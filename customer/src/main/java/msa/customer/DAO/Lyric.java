package msa.customer.DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@DynamoDBTable(tableName = "delivery_customer")
@Getter
@AllArgsConstructor
public class Lyric {
    @DynamoDBHashKey
    private String id;

    @DynamoDBAttribute
    private String lyrics;
}
