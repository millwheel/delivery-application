package msa.restaurant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.restaurant.entity.FoodKindType;
import msa.restaurant.entity.Menu;
import msa.restaurant.entity.Store;
import org.springframework.data.geo.Point;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class StoreDto {
    private String storeId;
    private String name;
    private FoodKindType foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private double longitude;
    private double latitude;
    private String introduction;

    public StoreDto(Store store){
        storeId = store.getStoreId();
        name = store.getName();
        foodKind = store.getFoodKind();
        phoneNumber = store.getPhoneNumber();
        address = store.getAddress();
        addressDetail = store.getAddressDetail();
        longitude = store.getLocation().getX();
        latitude = store.getLocation().getY();
        introduction = store.getIntroduction();
    }
}