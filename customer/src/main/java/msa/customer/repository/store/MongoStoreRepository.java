package msa.customer.repository.store;

import msa.customer.entity.menu.Menu;
import msa.customer.entity.store.FoodKindType;
import msa.customer.entity.store.Store;
import msa.customer.dto.store.StoreSqsDto;
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
    public String createStore(Store store) {
        Store savedStore = repository.save(store);
        return savedStore.getStoreId();
    }

    @Override
    public Optional<Store> readStore(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Store> readStoreNearLocation(Point location, FoodKindType foodKind) {
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
