package msa.restaurant.repository;

import msa.restaurant.DAO.Member;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryMemberRepository implements MemberRepository{
    private static Map<Long, Member> storage = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public void make(Member member) {
        member.setId(sequence++);
        storage.put(member.getId(),member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return storage.values().stream().filter(member -> member.getEmail().equals(email)).findAny();
    }
}
