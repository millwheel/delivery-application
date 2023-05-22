package msa.customer.repository.restaurant;

import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import org.springframework.context.annotation.Primary;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Repository;
import org.springframework.data.geo.Point;

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
    public String make(Restaurant restaurant) {
        Restaurant savedRestaurant = repository.save(restaurant);
        return savedRestaurant.getId();
    }

    @Override
    public Optional<Restaurant> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Restaurant> findRestaurantNear(Point location) {
        Distance distance = new Distance(4000);
        return repository.findByLocationNear(location, distance);
    }

    @Override
    public List<Restaurant> findAll() {
        return repository.findAll();
    }


    @Override
    public void setName(String id, String name) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setName(name);
            repository.save(restaurant);
        });
    }

    @Override
    public void setPhoneNumber(String id, String phoneNumber) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setPhoneNumber(phoneNumber);
            repository.save(restaurant);
        });
    }

    @Override
    public void setAddress(String id, String address) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setAddress(address);
            repository.save(restaurant);
        });
    }

    @Override
    public void setAddressDetail(String id, String addressDetail) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setAddressDetail(addressDetail);
            repository.save(restaurant);
        });
    }

    @Override
    public void setLocation(String id, Point location) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setLocation(location);
            repository.save(restaurant);
        });
    }

    @Override
    public void setIntroduction(String id, String introduction) {
        repository.findById(id).ifPresent(restaurant -> {
            restaurant.setIntroduction(introduction);
            repository.save(restaurant);
        });
    }

    @Override
    public void setMenuList(String id, List<Menu> menuList) {
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
