package msa.restaurant.repository.member;

import msa.restaurant.dto.ManagerDto;
import msa.restaurant.entity.Manager;
import msa.restaurant.entity.Store;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    String create(Manager manager);
    Optional<Manager> findById(String managerId);
    void update(String managerId, ManagerDto data);
    void updateStoreList(String managerId, List<Store> store);
    void deleteStoreFromList(String managerId, Store store);
}

