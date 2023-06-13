package msa.restaurant.repository.store;

import msa.restaurant.dto.StoreRequestDto;
import msa.restaurant.entity.Menu;
import msa.restaurant.entity.Store;
import msa.restaurant.dto.StoreSqsDto;
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
    public String create(Store store) {
        Store savedStore = repository.save(store);
        return savedStore.getStoreId();
    }

    @Override
    public Optional<Store> findById(String storeId) {
        return repository.findById(storeId);
    }

    @Override
    public void update(String storeId, StoreRequestDto data) {
        repository.findById(storeId).ifPresent(store -> {
            store.setName(data.getName());
            store.setFoodKind(data.getFoodKind());
            store.setPhoneNumber(data.getPhoneNumber());
            store.setAddress(data.getAddress());
            store.setAddressDetail(data.getAddressDetail());
            store.setIntroduction(data.getIntroduction());
            repository.save(store);
        });
    }

    @Override
    public void updateLocation(String storeId, Point location) {
        repository.findById(storeId).ifPresent(store -> {
            store.setLocation(location);
            repository.save(store);
        });
    }

    @Override
    public void updateMenuList(String storeId, List<Menu> menuList) {
        repository.findById(storeId).ifPresent(store -> {
            store.setMenuList(menuList);
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


