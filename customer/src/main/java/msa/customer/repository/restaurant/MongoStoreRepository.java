package msa.customer.repository.restaurant;

import msa.customer.DAO.FoodKindType;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Store;
import msa.customer.DTO.StoreForm;
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
//            store.setLocation(data.getLocation());
            store.setMenuList(data.getMenuList());
            repository.save(store);
        });
    }


    @Override
    public void updateName(String id, String name) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setName(name);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateFoodKind(String id, FoodKindType foodKind) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setFoodKind(foodKind);
            repository.save(restaurant);
        });
    }

    @Override
    public void updatePhoneNumber(String id, String phoneNumber) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setPhoneNumber(phoneNumber);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateAddress(String id, String address) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setAddress(address);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateAddressDetail(String id, String addressDetail) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setAddressDetail(addressDetail);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateLocation(String id, Point location) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setLocation(location);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateIntroduction(String id, String introduction) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setIntroduction(introduction);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateMenuList(String id, List<Menu> menuList) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setMenuList(menuList);
            repository.save(restaurant);
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
    public void deleteAll() {
        repository.deleteAll();
    }
}
