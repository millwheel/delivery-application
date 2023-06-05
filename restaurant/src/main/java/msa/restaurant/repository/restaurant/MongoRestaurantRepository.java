package msa.restaurant.repository.restaurant;

import msa.restaurant.DAO.FoodKindType;
import msa.restaurant.DAO.Menu;
import msa.restaurant.DAO.Store;
import msa.restaurant.DTO.RestaurantForm;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoRestaurantRepository implements RestaurantRepository{
    private final SpringDataMongoRestaurantRepository repository;

    public MongoRestaurantRepository(SpringDataMongoRestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Store store) {
        Store savedStore = repository.save(store);
        return savedStore.getRestaurantId();
    }

    @Override
    public Optional<Store> findById(String restaurantId) {
        return repository.findById(restaurantId);
    }

    @Override
    public List<Store> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(String restaurantId, RestaurantForm data) {
        repository.findById(restaurantId).ifPresent(existing -> {
            existing.setName(data.getName());
            existing.setFoodKind(data.getFoodKind());
            existing.setPhoneNumber(data.getPhoneNumber());
            existing.setAddress(data.getAddress());
            existing.setAddressDetail(data.getAddressDetail());
            existing.setIntroduction(data.getIntroduction());
            repository.save(existing);
        });
    }

    @Override
    public void updateName(String restaurantId, String name) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setName(name);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateFoodKind(String restaurantId, FoodKindType foodKind) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setFoodKind(foodKind);
            repository.save(restaurant);
        });
    }

    @Override
    public void updatePhoneNumber(String restaurantId, String phoneNumber) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setPhoneNumber(phoneNumber);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateAddress(String restaurantId, String address) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setAddress(address);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateAddressDetail(String restaurantId, String addressDetail) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setAddressDetail(addressDetail);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateLocation(String restaurantId, Point location) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setLocation(location);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateIntroduction(String restaurantId, String introduction) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setIntroduction(introduction);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateMenuList(String restaurantId, List<Menu> menuList) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setMenuList(menuList);
            repository.save(restaurant);
        });
    }

    @Override
    public void updateOpenStatus(String restaurantId, boolean status) {
        repository.findById(restaurantId).ifPresent(restaurant -> {
            restaurant.setOpen(status);
            repository.save(restaurant);
        });
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}


