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
        return repository.save(store);
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
    public Store update(String storeId, StoreRequestDto data, Point location) {
        Store store = repository.findById(storeId).orElseThrow();
        if (data.getName() != null) {
            store.setName(data.getName());
        }
        if (data.getFoodKind() != null) {
            store.setFoodKind(data.getFoodKind());
        }
        if (data.getPhoneNumber() != null) {
            store.setPhoneNumber(data.getPhoneNumber());
        }
        if (data.getAddress() != null) {
            store.setAddress(data.getAddress());
        }
        if (data.getAddressDetail() != null) {
            store.setAddressDetail(data.getAddressDetail());
        }
        if (data.getIntroduction() != null) {
            store.setIntroduction(data.getIntroduction());
        }
        if (location != null) {
            store.setLocation(location);
        }
        return repository.save(store);
    }



    @Override
    public void updateOpenStatus(String storeId, boolean status) {
        repository.findById(storeId).ifPresent(store -> {
            store.setOpen(status);
            repository.save(store);
        });
    }

    @Override
    public boolean delete(String storeId) {
        if (repository.existsById(storeId)){
            repository.deleteById(storeId);
            return true;
        }
        return false;
    }
}


