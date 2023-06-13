package msa.restaurant.repository.member;

import msa.restaurant.entity.Manager;
import msa.restaurant.entity.Store;
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
    public void updateStoreList(String managerId, List<Store> storeList) {
        repository.findById(managerId).ifPresent(manager -> {
            manager.setStoreList(storeList);
            repository.save(manager);
        });
    }

    @Override
    public void deleteStoreFromList(String managerId) {
        repository.findById(managerId).ifPresent(manager -> {
            List<Store> storeList = manager.getStoreList();
        });
    }

}