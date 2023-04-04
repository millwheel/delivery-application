package msa.restaurant.DAO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document
public class Store {
    @Id
    private Long id;
    private String name;
    private String address;
    private List menuList;
}
