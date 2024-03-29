package msa.customer.repository.store;

import msa.customer.entity.store.FoodKind;
import msa.customer.entity.store.Store;
import msa.customer.dto.store.StoreSqsDto;
import msa.customer.exception.StoreNonexistentException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.geo.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MongoStoreRepository implements StoreRepository {

    private final SpringDataMongoStoreRepository repository;

    public MongoStoreRepository(SpringDataMongoStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Store createStore(Store store) {
        Store savedStore = repository.save(store);
        return savedStore;
    }

    @Override
    public Store readStore(String storeId) {
        return repository.findById(storeId).orElseThrow(() -> new StoreNonexistentException(storeId));
    }

    @Override
    public List<Store> readStoreListNearLocation(Point location, FoodKind foodKind) {
        Distance distance = new Distance(4, Metrics.KILOMETERS);
        return repository.findByLocationNearAndFoodKindIs(location, distance, foodKind);
    }

    @Override
    public void updateStore(StoreSqsDto data) {
        repository.findById(data.getStoreId()).ifPresent(store -> {
            store.setName(data.getName());
            store.setFoodKind(data.getFoodKind());
            store.setPhoneNumber(data.getPhoneNumber());
            store.setAddress(data.getAddress());
            store.setAddressDetail(data.getAddressDetail());
            store.setLocation(data.getLocation());
            repository.save(store);
        });
    }

    @Override
    public void updateOpenStatus(String id, boolean status) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setOpen(status);
            repository.save(restaurant);
        });
    }

    @Override
    public void deleteStore(String storeId) {
        repository.deleteById(storeId);
    }

}
