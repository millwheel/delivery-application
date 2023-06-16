package msa.restaurant.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
public class MenuPartInfo {
    private String menuId;
    private String name;
    private int price;
}
