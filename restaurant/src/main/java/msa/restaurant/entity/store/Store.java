package msa.restaurant.entity.store;

import lombok.Builder;
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

    @Builder
    public Store(String storeId, String name, FoodKind foodKind, String phoneNumber, String address, String addressDetail, Point location, String introduction, Boolean open, String managerId) {
        this.storeId = storeId;
        this.name = name;
        this.foodKind = foodKind;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.location = location;
        this.introduction = introduction;
        this.open = open;
        this.managerId = managerId;
    }
}