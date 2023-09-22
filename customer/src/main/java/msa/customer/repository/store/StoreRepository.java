package msa.customer.repository.store;

import msa.customer.entity.store.FoodKind;
import msa.customer.entity.store.Store;
import msa.customer.dto.store.StoreSqsDto;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    Store createStore(Store store);
    Store readStore(String storeId);
    List<Store> readStoreListNearLocation(Point location, FoodKind foodKind);
    void updateStore(StoreSqsDto data);
    void updateOpenStatus(String id, boolean open);
    void deleteStore(String id);
}
