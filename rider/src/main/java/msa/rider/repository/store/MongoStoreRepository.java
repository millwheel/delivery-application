package msa.rider.repository.store;

import msa.rider.dto.StoreSqsDto;
import msa.rider.entity.FoodKindType;
import msa.rider.entity.MenuPartInfo;
import msa.rider.entity.Store;
import org.springframework.context.annotation.Primary;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
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
    public String create(Store store) {
        Store savedStore = repository.save(store);
        return savedStore.getStoreId();
    }

    @Override
    public Optional<Store> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Store> findStoreNear(Point location, FoodKindType foodKind) {
        Distance distance = new Distance(4, Metrics.KILOMETERS);
        return repository.findByLocationNearAndFoodKindIs(location, distance, foodKind);
    }

    @Override
    public void update(StoreSqsDto data) {
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
    public void updateMenuList(String id, List<MenuPartInfo> menuList) {
        repository.findById(id).ifPresent(store -> {
            store.setMenuList(menuList);
            repository.save(store);
        });
    }

    @Override
    public void updateOpenStatus(String id, boolean status) {
        repository.findById(id).ifPresent(store -> {
            store.setOpen(status);
            repository.save(store);
        });
    }

    @Override
    public void deleteById(String storeId) {
        repository.deleteById(storeId);
    }

}
