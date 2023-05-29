package msa.restaurant.repository.member;

import msa.restaurant.DAO.Member;
import msa.restaurant.DAO.Restaurant;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    void make(Member member);
    Optional<Member> findById(String id);
    void setName(String id, String name);
    void setPhoneNumber(String id, String phoneNumber);
    void setRestaurantList(String id, String restaurantId);
}

