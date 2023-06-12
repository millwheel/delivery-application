package msa.customer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document("order")
@NoArgsConstructor
public class Order {
    @MongoId
    private String orderId;
    private String customerId;
    private String restaurantId;
    private List<Menu> menuList;
    private int totalPrice;
}
