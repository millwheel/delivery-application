package msa.restaurant.repository.member;

import msa.restaurant.dto.manager.ManagerRequestDto;
import msa.restaurant.entity.member.Manager;
import org.springframework.stereotype.Repository;
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

}