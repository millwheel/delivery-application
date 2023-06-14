package msa.restaurant.dto.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.restaurant.entity.FoodKindType;
import msa.restaurant.entity.Store;
import org.springframework.data.geo.Point;


@Getter
@Setter
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

    public StoreSqsDto(Store store){
        storeId = store.getStoreId();
        name = store.getName();
        foodKind = store.getFoodKind();
        phoneNumber = store.getPhoneNumber();
        address = store.getAddress();
        addressDetail = store.getAddressDetail();
        location = store.getLocation();
        introduction = store.getIntroduction();
    }
}
