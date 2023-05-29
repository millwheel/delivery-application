package msa.restaurant.DAO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Setter
@Getter
@NoArgsConstructor
@Document("restaurant")
public class Restaurant {

    @MongoId
    private String restaurantId;
    private String name;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point coordinates;
}
