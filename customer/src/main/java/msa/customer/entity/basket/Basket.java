package msa.customer.entity.basket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Setter
@Document("basket")
@NoArgsConstructor
public class Basket {
    @MongoId
    private String basketId;
    private List<MenuInBasket> menuInBasketList;
    private String storeId;
    private int totalPrice;
}
