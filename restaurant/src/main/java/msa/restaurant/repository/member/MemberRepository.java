package msa.restaurant.repository.member;

import msa.restaurant.DAO.Manager;
import msa.restaurant.DAO.Restaurant;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void make(Manager manager);
    Optional<Manager> findById(String id);
    void setName(String id, String name);
    void setPhoneNumber(String id, String phoneNumber);
    void setRestaurantList(String id, List<Restaurant> restaurant);
}

