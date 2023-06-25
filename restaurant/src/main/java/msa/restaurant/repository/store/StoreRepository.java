package msa.restaurant.repository.store;

import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.entity.Store;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    String create(Store store);
    Optional<Store> readStore(String storeId);
    Optional<List<Store>> readStoreList(String managerId);
    void update(String storeId, StoreRequestDto data);
    void updateLocation(String storeId, Point location);
    void updateOpenStatus(String storeId, boolean open);
    void deleteById(String storeId);
}
