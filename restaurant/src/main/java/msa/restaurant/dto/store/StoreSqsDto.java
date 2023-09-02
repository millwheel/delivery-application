package msa.restaurant.dto.store;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.store.FoodKind;
import msa.restaurant.entity.store.Store;
import org.springframework.data.geo.Point;


@Getter
@Setter
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

    public StoreSqsDto(Store store){
        storeId = store.getStoreId();
        name = store.getName();
        foodKind = store.getFoodKind();
        phoneNumber = store.getPhoneNumber();
        address = store.getAddress();
        addressDetail = store.getAddressDetail();
        location = store.getLocation();
        introduction = store.getIntroduction();
        open = store.getOpen();
    }
}
