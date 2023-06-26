package msa.restaurant.dto.store;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.store.FoodKind;

@Getter
@Setter
public class StoreRequestDto {
    private String name;
    private FoodKind foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String introduction;
}
