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
    /*
        (R) Spring 내장 클래스인 Point를 사용하고 계신데, Location 클래스를 직접 만들어주시는 것이 어떨까 싶습니다.
        delivery-application 내에서 location은 비중이 큰 역할을 할텐데, 이를 spring 내장 클래스로 처리하니
        제약 사항이 많고 도메인에 맞는 기능을 제공하는 것이 어려워울 것 같습니다.
    */
    private Point location;

}