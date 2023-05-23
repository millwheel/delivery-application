package msa.customer.repository.restaurant;

import msa.customer.DAO.FoodKindType;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import msa.customer.DTO.RestaurantForm;
import org.springframework.context.annotation.Primary;
import org.springframework.data.geo.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MongoRestaurantRepository implements RestaurantRepository{

    private final SpringDataMongoRestaurantRepository repository;

    public MongoRestaurantRepository(SpringDataMongoRestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Restaurant restaurant) {
        Restaurant savedRestaurant = repository.save(restaurant);
        return savedRestaurant.getId();
    }

    @Override
    public Optional<Restaurant> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Restaurant> findRestaurantNear(Point location, FoodKindType foodKind) {
        Distance distance = new Distance(4, Metrics.KILOMETERS);
        return repository.findByLocationNearAndFoodKindIs(location, distance, foodKind);
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    @Override
    public void update(String id, RestaurantForm data) {
        repository.findById(id).ifPresent(existing -> {
            existing.setName(data.getName());
            existing.setFoodKind(data.getFoodKind());
            existing.setPhoneNumber(data.getPhoneNumber());
            existing.setAddress(data.getAddress());
            existing.setAddressDetail(data.getAddressDetail());
            existing.setLocation(data.getLocation());
            existing.setIntroduction(data.getIntroduction());
            existing.setMenuList(data.getMenuList());
            repository.save(existing);
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
    public void setOpen(String id, boolean open) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setOpen(open);
            repository.save(restaurant);
        });
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
}
