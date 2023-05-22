package msa.customer.repository.member;

import msa.customer.DAO.Member;
import org.springframework.data.geo.Point;

import java.util.Optional;

public interface MemberRepository {
    void make(Member member);
    Optional<Member> findById(String id);

    void setName(String id, String name);

    void setPhoneNumber(String id, String phoneNumber);

    void setAddress(String id, String address);

    void setAddressDetail(String id, String addressDetail);

    void setCoordinates(String id, Point coordinates);

    void deleteAll();
}
