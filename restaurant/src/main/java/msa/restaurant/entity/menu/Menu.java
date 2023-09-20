package msa.restaurant.entity.menu;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
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
    @Indexed
    private String storeId;

    @Builder
    public Menu(String name, int price, String description, String storeId) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.storeId = storeId;
    }
}
