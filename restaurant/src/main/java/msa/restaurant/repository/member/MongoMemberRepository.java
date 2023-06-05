package msa.restaurant.repository.member;

import msa.restaurant.DAO.Manager;
import msa.restaurant.DAO.Restaurant;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoMemberRepository implements MemberRepository{

    private final SpringDataMongoMemberRepository repository;

    public MongoMemberRepository(SpringDataMongoMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Manager manager) {
        Manager savedManager = repository.save(manager);
        return savedManager.getManagerId();
    }

    @Override
    public Optional<Manager> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void updateName(String managerId, String name){
        repository.findById(managerId).ifPresent(manager -> {
            manager.setName(name);
            repository.save(manager);
        });
    }

    @Override
    public void updatePhoneNumber(String managerId, String phoneNumber){
        repository.findById(managerId).ifPresent(manager -> {
            manager.setPhoneNumber(phoneNumber);
            repository.save(manager);
        });
    }

    @Override
    public void updateRestaurantList(String managerId, List<Restaurant> restaurantList) {
        repository.findById(managerId).ifPresent(manager -> {
            manager.setRestaurantList(restaurantList);
            repository.save(manager);
        });
    }

}