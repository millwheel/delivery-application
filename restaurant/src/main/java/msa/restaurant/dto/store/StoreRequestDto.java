package msa.restaurant.dto.store;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.store.FoodKind;

@Getter
@Setter
public class StoreRequestDto {
    @NotNull(message = "store name is missing")
    private String name;
    @NotNull(message = "store foodKind is missing")
    private FoodKind foodKind;
    @NotNull(message = "store phone number is missing")
    private String phoneNumber;
    @NotNull(message = "store address is missing")
    private String address;
    @NotNull(message = "store address detail is missing")
    private String addressDetail;
    @NotNull(message = "introduction detail is missing")
    private String introduction;
}
