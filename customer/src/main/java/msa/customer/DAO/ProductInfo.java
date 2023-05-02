package msa.customer.DAO;

import com.amazonaws.services.dynamodbv2.datamodeling.*;

@DynamoDBTable(tableName = "ProductInfo")
public class ProductInfo {
    private String id;
    private String msrp;
    private String cost;

    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    public String getId(){
        return id;
    }

    @DynamoDBAttribute
    public String getMsrp(){
        return msrp;
    }

    @DynamoDBAttribute
    public String getCost(){
        return cost;
    }
}