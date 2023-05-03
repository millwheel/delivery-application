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
    public void setName(String id, String name){
        repository.findById(id).ifPresent(member -> member.setName(name));
    }

    @Override
    public void setPhoneNumber(String id, String phoneNumber){
        repository.findById(id).ifPresent(member -> member.setPhoneNumber(phoneNumber));
    }

    @Override
    public void setAddress(String id, String address){
        repository.findById(id).ifPresent(member -> member.setAddress(address));
    }

}
