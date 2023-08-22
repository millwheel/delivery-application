package msa.customer.dto.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.customer.entity.store.FoodKind;
import org.springframework.data.geo.Point;

@Getter
@Setter
@NoArgsConstructor
public class StoreSqsDto {
    private String storeId;
    private String name;
    private FoodKind foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;
    private String introduction;
    private Boolean open;

    public StoreSqsDto(String storeId, String name, FoodKind foodKind, String phoneNumber, String address, String addressDetail, Point location, String introduction, Boolean open) {
        this.storeId = storeId;
        this.name = name;
        this.foodKind = foodKind;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.location = location;
        this.introduction = introduction;
        this.open = open;
    }
}
