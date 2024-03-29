package msa.restaurant.dto.store;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.store.FoodKind;
import msa.restaurant.entity.store.Store;

@Getter
@Setter
public class StorePartResponseDto {
    private String storeId;
    private String name;
    private FoodKind foodKind;
    private String address;
    private String addressDetail;

    public StorePartResponseDto(Store store) {
        storeId = store.getStoreId();
        name = store.getName();
        address = store.getAddress();
        foodKind = store.getFoodKind();
        address = store.getAddress();
        addressDetail = store.getAddressDetail();
    }
}
