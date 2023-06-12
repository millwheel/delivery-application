package msa.restaurant.dto;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.FoodKindType;

@Getter
@Setter
public class StoreRequestDto {
    private String name;
    private FoodKindType foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    private String introduction;
}
