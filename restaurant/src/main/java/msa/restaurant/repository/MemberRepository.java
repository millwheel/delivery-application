package msa.restaurant.repository;

import msa.restaurant.DAO.Member;

import java.util.Optional;

public interface MemberRepository {

    public void make(Member member);
    Optional<Member> findByEmail(String email);

}
