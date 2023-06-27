package msa.rider.repository.store;

import msa.rider.dto.StoreSqsDto;
import msa.rider.entity.store.Store;
import java.util.Optional;

public interface StoreRepository {
    String create(Store store);
    Optional<Store> findById(String id);
    void updateStore(StoreSqsDto data);
    void updateOpenStatus(String id, boolean open);
    void deleteById(String id);
}
