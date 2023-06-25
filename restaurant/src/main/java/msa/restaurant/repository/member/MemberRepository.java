package msa.restaurant.repository.member;

import msa.restaurant.dto.manager.ManagerRequestDto;
import msa.restaurant.entity.Manager;
import msa.restaurant.entity.StorePartInfo;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    String create(Manager manager);
    Optional<Manager> findById(String managerId);
    void update(String managerId, ManagerRequestDto data);
}

