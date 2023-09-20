package msa.restaurant.repository.store;

import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.entity.store.Store;
import msa.restaurant.exception.StoreMismatchException;
import msa.restaurant.exception.StoreNonexistentException;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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
    public Store readStore(String managerId, String storeId) {
        Store store = repository.findById(storeId).orElseThrow(() -> new StoreNonexistentException(storeId));
        if (!store.getManagerId().equals(managerId)) throw new StoreMismatchException(storeId, managerId);
        return store;
    }

    @Override
    public List<Store> readStoreList(String managerId) {
        return repository.findByManagerId(managerId);
    }

    @Override
    public Store update(String managerId, String storeId, StoreRequestDto data, Point location) {
        Store store = repository.findById(storeId).orElseThrow(() -> new StoreNonexistentException(storeId));
        if(!Objects.equals(store.getManagerId(), managerId)){
            throw new StoreMismatchException(storeId, managerId);
        }
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
    public void updateOpenStatus(String managerId, String storeId, boolean status) {
        Store store = repository.findById(storeId).orElseThrow(() -> new StoreNonexistentException(storeId));
        if(!Objects.equals(store.getManagerId(), managerId)){
            throw new StoreMismatchException(storeId, managerId);
        }
        store.setOpen(status);
        repository.save(store);
    }

    @Override
    public void delete(String managerId, String storeId) {
        Store store = repository.findById(storeId).orElseThrow(() -> new StoreNonexistentException(storeId));
        if(!Objects.equals(store.getManagerId(), managerId)){
            throw new StoreMismatchException(storeId, managerId);
        }
        repository.deleteById(storeId);
    }
}


