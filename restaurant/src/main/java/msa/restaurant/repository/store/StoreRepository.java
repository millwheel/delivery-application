package msa.restaurant.repository.store;

import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.entity.store.Store;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    Store create(Store store);
    Store readStore(String managerId, String storeId);
    List<Store> readStoreList(String managerId);
    Store update(String managerId, String storeId, StoreRequestDto data, Point location);
    Store updateOpenStatus(String managerId, String storeId, boolean open);
    void delete(String managerId, String storeId);
}