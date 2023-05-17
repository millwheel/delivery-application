package msa.customer.repository.member;

import msa.customer.DAO.Location;
import msa.customer.DAO.Member;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public class MongoMemberRepository implements MemberRepository {

    private final SpringDataMongoMemberRepository repository;

    public MongoMemberRepository(SpringDataMongoMemberRepository repository) {
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
            member.setAddressDetail(addressDetail);
            repository.save(member);
        });
    }

    @Override
    public void setCoordinates(String id, Location location) {
        repository.findById(id).ifPresent(member -> {
            member.setLocation(location);
            repository.save(member);
        });
    }

    @Override
    public void deleteAll(){
        repository.deleteAll();
    }

}
