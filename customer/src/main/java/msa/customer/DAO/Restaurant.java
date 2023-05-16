package msa.customer.DAO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Document("restaurant")
public class Restaurant {
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Coordinates coordinates;
    private String introduction;
    private List<Menu> menuList;
}
