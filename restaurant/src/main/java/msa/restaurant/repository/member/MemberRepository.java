package msa.restaurant.repository.member;

import msa.restaurant.entity.Manager;
import msa.restaurant.entity.Store;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    String create(Manager manager);
    Optional<Manager> findById(String managerId);
    void updateName(String managerId, String name);
    void updatePhoneNumber(String managerId, String phoneNumber);
    void updateStoreList(String managerId, List<Store> store);
    void deleteStoreFromList(String managerId);
}

