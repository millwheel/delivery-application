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
    public void setName(String managerId, String name){
        repository.findById(managerId).ifPresent(manager -> {
            manager.setName(name);
            repository.save(manager);
        });
    }

    @Override
    public void setPhoneNumber(String managerId, String phoneNumber){
        repository.findById(managerId).ifPresent(manager -> {
            manager.setPhoneNumber(phoneNumber);
            repository.save(manager);
        });
    }

    @Override
    public void setRestaurantList(String managerId, List<Restaurant> restaurantList) {
        repository.findById(managerId).ifPresent(manager -> {
            manager.setRestaurantList(restaurantList);
            repository.save(manager);
        });
    }

}