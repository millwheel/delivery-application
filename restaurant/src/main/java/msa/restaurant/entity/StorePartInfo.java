package msa.restaurant.entity;

import lombok.Getter;

@Getter
public class StorePartInfo {
    private final String storeId;
    private final String name;
    private final String address;

    public StorePartInfo(Store store) {
        storeId = store.getStoreId();
        name = store.getName();
        address = store.getAddress();
    }
}
