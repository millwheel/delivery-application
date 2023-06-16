package msa.customer.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@Document("menu")
@NoArgsConstructor
public class Menu {
    @MongoId
    private String menuId;
    private String name;
    private int price;
    private String description;
}
