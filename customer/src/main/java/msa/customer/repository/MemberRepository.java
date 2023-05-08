package msa.customer.repository;

import msa.customer.DAO.Member;

import java.util.Optional;

public interface MemberRepository {
    public void make(Member member);
    Optional<Member> findById(String id);

    void setName(String id, String name);

    void setPhoneNumber(String id, String phoneNumber);

    void setAddress(String id, String address);

    void setAddressDetail(String id, String addressDetail);
}
