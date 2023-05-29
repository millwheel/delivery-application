package msa.restaurant.repository.member;

import msa.restaurant.DAO.Manager;
import msa.restaurant.DAO.Restaurant;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class MongoMemberRepository implements MemberRepository{

    private final SpringDataMongoMemberRepository repository;

    public MongoMemberRepository(SpringDataMongoMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public void make(Manager manager) {
        repository.save(manager);
    }

    @Override
    public Optional<Manager> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void setName(String id, String name){
        repository.findById(id).ifPresent(manager -> {
            manager.setName(name);
            repository.save(manager);
        });
    }

    @Override
    public void setPhoneNumber(String id, String phoneNumber){
        repository.findById(id).ifPresent(manager -> {
            manager.setPhoneNumber(phoneNumber);
            repository.save(manager);
        });
    }

    @Override
    public void setRestaurantList(String id, List<Restaurant> restaurantList) {
        repository.findById(id).ifPresent(manager -> {
            manager.setRestaurantList(restaurantList);
            repository.save(manager);
        });
    }

}