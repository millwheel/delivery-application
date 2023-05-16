package msa.customer.DAO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("menu")
@NoArgsConstructor
public class Menu {

    private String name;
    private String price;
    private String description;
}
