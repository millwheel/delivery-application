package msa.customer.repository.restaurant;

import msa.customer.DAO.Coordinates;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Restaurant;
import org.springframework.context.annotation.Primary;
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
    public String make(Restaurant restaurant) {
        Restaurant savedRestaurant = repository.save(restaurant);
        return savedRestaurant.getId();
    }

    @Override
    public Optional<Restaurant> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void setName(String id, String name) {
        repository.findById(id).ifPresent(member -> {
            member.setName(name);
            repository.save(member);
        });
    }

    @Override
    public void setPhoneNumber(String id, String phoneNumber) {
        repository.findById(id).ifPresent(member -> {
            member.setPhoneNumber(phoneNumber);
            repository.save(member);
        });
    }

    @Override
    public void setAddress(String id, String address) {
        repository.findById(id).ifPresent(member -> {
            member.setAddress(address);
            repository.save(member);
        });
    }

    @Override
    public void setAddressDetail(String id, String addressDetail) {
        repository.findById(id).ifPresent(member -> {
            member.setAddressDetail(addressDetail);
            repository.save(member);
        });
    }

    @Override
    public void setCoordinates(String id, Coordinates coordinates) {
        repository.findById(id).ifPresent(member -> {
            member.setCoordinates(coordinates);
            repository.save(member);
        });
    }

    @Override
    public void setIntroduction(String id, String introduction) {
        repository.findById(id).ifPresent(member -> {
            member.setIntroduction(introduction);
            repository.save(member);
        });
    }

    @Override
    public void setMenuList(String id, List<Menu> menuList) {
        repository.findById(id).ifPresent(member -> {
            member.setMenuList(menuList);
            repository.save(member);
        });
    }
}
