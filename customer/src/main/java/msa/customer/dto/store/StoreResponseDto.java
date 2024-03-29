package msa.customer.dto.store;

import lombok.Getter;
import lombok.Setter;
import msa.customer.entity.store.FoodKind;
import msa.customer.entity.store.Store;

@Getter
@Setter
public class StoreResponseDto {
    private String name;
    private FoodKind foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String introduction;

    public StoreResponseDto(Store store) {
        name = store.getName();
        foodKind = store.getFoodKind();
        phoneNumber = store.getPhoneNumber();
        address = store.getAddress();
        addressDetail = store.getAddressDetail();
        introduction = store.getIntroduction();
    }
}
