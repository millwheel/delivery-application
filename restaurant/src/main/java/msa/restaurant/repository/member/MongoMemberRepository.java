package msa.restaurant.repository.member;

import msa.restaurant.dto.manager.ManagerRequestDto;
import msa.restaurant.entity.Manager;
import msa.restaurant.entity.StorePartInfo;
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
    public void update(String managerId, ManagerRequestDto data) {
        repository.findById(managerId).ifPresent(manager -> {
            manager.setName(data.getName());
            manager.setPhoneNumber(data.getPhoneNumber());
        });
    }

    @Override
    public void updateStoreList(String managerId, List<StorePartInfo> storeList) {
        repository.findById(managerId).ifPresent(manager -> {
            manager.setStorePartInfoList(storeList);
            repository.save(manager);
        });
    }

    @Override
    public void deleteStoreFromList(String managerId, String storeId) {
        repository.findById(managerId).ifPresent(manager -> {
            List<StorePartInfo> storeList = manager.getStorePartInfoList();
            storeList.removeIf(store -> store.getStoreId().equals(storeId));
            manager.setStorePartInfoList(storeList);
            repository.save(manager);
        });
    }

}