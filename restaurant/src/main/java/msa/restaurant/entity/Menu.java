package msa.restaurant.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@NoArgsConstructor
@Document("menu")
public class Menu {
    @MongoId
    private String menuId;
    private String name;
    private int price;
    private String description;
    private Store store;
}
