package msa.restaurant.repository.member;

import msa.restaurant.DAO.Manager;
import msa.restaurant.DAO.Restaurant;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    String create(Manager manager);
    Optional<Manager> findById(String managerId);
    void updateName(String managerId, String name);
    void updatePhoneNumber(String managerId, String phoneNumber);
    void updateRestaurantList(String managerId, List<Restaurant> restaurant);
}

