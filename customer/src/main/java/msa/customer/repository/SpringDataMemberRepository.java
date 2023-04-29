package msa.customer.repository;

import msa.customer.DAO.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public class SpringDataMemberRepository implements MemberRepository {

    private final SpringDataCassandraMemberRepository repository;

    public SpringDataMemberRepository(SpringDataCassandraMemberRepository repository) {
        this.repository = repository;
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
