package msa.customer.repository;

import msa.customer.DAO.Coordinates;
import msa.customer.DAO.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DynamoMemberRepository implements MemberRepository{

    private final SpringDataDynamoMemberRepository repository;

    public DynamoMemberRepository(SpringDataDynamoMemberRepository repository) {
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
        repository.findById(id).ifPresent(member -> {
            member.setName(name);
            repository.save(member);
        });
    }

    @Override
    public void setPhoneNumber(String id, String phoneNumber){
        repository.findById(id).ifPresent(member -> {
            member.setPhoneNumber(phoneNumber);
            repository.save(member);
        });
    }

    @Override
    public void setAddress(String id, String address){
        repository.findById(id).ifPresent(member -> {
            member.setAddress(address);
            repository.save(member);
        });
    }

    @Override
    public void setAddressDetail(String id, String addressDetail) {
        repository.findById(id).ifPresent(member -> {
            member.setAddress(addressDetail);
            repository.save(member);
        });
    }

    @Override
    public void setCoordinates(String id, Coordinates coordinates) {
        repository.findById(id).ifPresent(member -> {
            member.setCoordinates(coordinates);
            repository.save(member);
        });
    }

}
