package msa.customer.repository;

import lombok.extern.slf4j.Slf4j;
import msa.customer.DAO.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
@Slf4j
public class SpringDataMemberRepository implements MemberRepository {

    private final SpringDataDynamoMemberRepository repository;

    public SpringDataMemberRepository(SpringDataDynamoMemberRepository repository) {
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
