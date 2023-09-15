package msa.restaurant.repository.store;

import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.entity.store.Store;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoStoreRepository implements StoreRepository {
    private final SpringDataMongoStoreRepository repository;

    public MongoStoreRepository(SpringDataMongoStoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Store create(Store store) {
        Store savedStore = repository.save(store);
        return savedStore;
    }

    @Override
    public Optional<Store> readStore(String storeId) {
        return repository.findById(storeId);
    }

    @Override
    public List<Store> readStoreList(String managerId) {
        return repository.findByManagerId(managerId);
    }

    @Override
    public Store update(String storeId, StoreRequestDto data) {
        Store store = repository.findById(storeId).orElseThrow();
        store.setName(data.getName());
        store.setFoodKind(data.getFoodKind());
        store.setPhoneNumber(data.getPhoneNumber());
        store.setAddress(data.getAddress());
        store.setAddressDetail(data.getAddressDetail());
        store.setIntroduction(data.getIntroduction());
        repository.save(store);
        return store;
    }

    @Override
    public void updateLocation(String storeId, Point location) {
        repository.findById(storeId).ifPresent(store -> {
            store.setLocation(location);
            repository.save(store);
        });
    }


    @Override
    public void updateOpenStatus(String storeId, boolean status) {
        repository.findById(storeId).ifPresent(store -> {
            store.setOpen(status);
            repository.save(store);
        });
    }

    @Override
    public void deleteById(String storeId) {
        repository.deleteById(storeId);
    }
}


