package msa.rider.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import msa.rider.entity.FoodKindType;
import org.springframework.data.geo.Point;

@Getter
@NoArgsConstructor
public class StoreSqsDto {
    private String storeId;
    private String name;
    private FoodKindType foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private Point location;
    private String introduction;

    public StoreSqsDto(String storeId, String name, FoodKindType foodKind, String phoneNumber, String address, String addressDetail, Point location, String introduction) {
        this.storeId = storeId;
        this.name = name;
        this.foodKind = foodKind;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.addressDetail = addressDetail;
        this.location = location;
        this.introduction = introduction;
    }
}
