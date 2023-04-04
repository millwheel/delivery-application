package msa.restaurant.repository;

import msa.restaurant.DAO.Member;

import java.util.Optional;

public interface MemberRepository {
    void make(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
}
