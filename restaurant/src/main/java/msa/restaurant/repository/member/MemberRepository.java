package msa.restaurant.repository.member;

import msa.restaurant.DAO.Manager;
import msa.restaurant.DAO.Restaurant;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void make(Manager manager);
    Optional<Manager> findById(String managerId);
    void setName(String managerId, String name);
    void setPhoneNumber(String managerId, String phoneNumber);
    void setRestaurantList(String managerId, List<Restaurant> restaurant);
}

