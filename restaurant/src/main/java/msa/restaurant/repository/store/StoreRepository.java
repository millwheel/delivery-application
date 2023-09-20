package msa.restaurant.repository.store;

import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.entity.store.Store;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    Store create(Store store);
    Store readStore(String storeId);
    List<Store> readStoreList(String managerId);
    Store update(String storeId, StoreRequestDto data, Point location);
    void updateOpenStatus(String storeId, boolean open);
    void delete(String storeId);
}