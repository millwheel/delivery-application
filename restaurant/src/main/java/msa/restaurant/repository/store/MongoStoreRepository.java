package msa.restaurant.repository.store;

import msa.restaurant.DAO.FoodKindType;
import msa.restaurant.DAO.Menu;
import msa.restaurant.DAO.Store;
import msa.restaurant.DTO.StoreForm;
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
    public List<Store> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(String storeId, StoreForm data) {
        repository.findById(storeId).ifPresent(store -> {
            store.setName(data.getName());
            store.setFoodKind(data.getFoodKind());
            store.setPhoneNumber(data.getPhoneNumber());
            store.setAddress(data.getAddress());
            store.setAddressDetail(data.getAddressDetail());
            store.setLocation(data.getLocation());
            store.setIntroduction(data.getIntroduction());
            store.setMenuList(data.getMenuList());
            repository.save(store);
        });
    }

    @Override
    public void updateName(String storeId, String name) {
        repository.findById(storeId).ifPresent(store -> {
            store.setName(name);
            repository.save(store);
        });
    }

    @Override
    public void updateFoodKind(String storeId, FoodKindType foodKind) {
        repository.findById(storeId).ifPresent(store -> {
            store.setFoodKind(foodKind);
            repository.save(store);
        });
    }

    @Override
    public void updatePhoneNumber(String storeId, String phoneNumber) {
        repository.findById(storeId).ifPresent(store -> {
            store.setPhoneNumber(phoneNumber);
            repository.save(store);
        });
    }

    @Override
    public void updateAddress(String storeId, String address) {
        repository.findById(storeId).ifPresent(store -> {
            store.setAddress(address);
            repository.save(store);
        });
    }

    @Override
    public void updateAddressDetail(String storeId, String addressDetail) {
        repository.findById(storeId).ifPresent(store -> {
            store.setAddressDetail(addressDetail);
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
    public void updateIntroduction(String storeId, String introduction) {
        repository.findById(storeId).ifPresent(store -> {
            store.setIntroduction(introduction);
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
    public void deleteAll() {
        repository.deleteAll();
    }
}


