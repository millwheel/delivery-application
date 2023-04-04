package msa.rider.repository;

import msa.rider.DAO.Member;

import java.util.Optional;

public interface MemberRepository {

    public void make(Member member);
    Optional<Member> findByEmail(String email);

}
