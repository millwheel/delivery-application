package msa.customer.repository;

import msa.customer.DAO.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public class SpringDataMemberRepository implements MemberRepository{

    private final SpringDataDynamoMemberRepository repository;

    public SpringDataMemberRepository(SpringDataDynamoMemberRepository repository) {
        this.repository = repository;
    }
    @Override
    public void make(Member member) {
        repository.save(member);
    }

    @Override
    public Optional<Member> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void setPhoneNumber(String id, String phoneNumber){
        Optional<Member> member = repository.findById(id);

    }

}
