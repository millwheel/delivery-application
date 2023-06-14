package msa.restaurant.dto.store;

import lombok.Getter;
import lombok.Setter;
import msa.restaurant.entity.Store;
import msa.restaurant.entity.StorePartInfo;

@Getter
@Setter
public class StorePartInfoResponseDto {
    private String name;
    private String address;

    public StorePartInfoResponseDto(StorePartInfo storePartInfo) {
        name = storePartInfo.getName();
        address = storePartInfo.getAddress();
    }
}
