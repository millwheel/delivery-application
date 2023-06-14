package msa.restaurant.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StorePartInfo {
    private String storeId;
    private String name;
    private String address;

    public StorePartInfo(Store store) {
        storeId = store.getStoreId();
        name = store.getName();
        address = store.getAddress();
    }
}
