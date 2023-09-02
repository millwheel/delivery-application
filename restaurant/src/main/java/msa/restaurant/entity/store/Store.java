package msa.restaurant.entity.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@NoArgsConstructor
@Document("store")
public class Store {
    @MongoId
    private String storeId;
    private String name;
    private FoodKind foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;
    private String introduction;
    private Boolean open;
    @Indexed
    private String managerId;
}