package msa.restaurant.repository;

import msa.restaurant.DAO.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public class SpringDataMemberRepository implements MemberRepository{

    private final SpringDataMongoMemberRepository repository;

    public SpringDataMemberRepository(SpringDataMongoMemberRepository springDataMongoMemberRepository) {
        this.repository = springDataMongoMemberRepository;
    }

    @Override
    public void make(Member member) {
        repository.save(member);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return repository.findByEmail(email);
    }
}
