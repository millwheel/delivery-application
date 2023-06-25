package msa.restaurant.dto.store;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.Store;

@Getter
@Setter
public class StorePartResponseDto {
    private String name;
    private String address;

    public StorePartResponseDto(Store store) {
        name = store.getName();
        address = store.getAddress();
    }
}
