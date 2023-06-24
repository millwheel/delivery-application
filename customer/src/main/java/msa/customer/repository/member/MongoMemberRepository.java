package msa.customer.repository.member;

import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Primary
@Repository
@Slf4j
public class MongoMemberRepository implements MemberRepository {

    private final SpringDataMongoMemberRepository repository;

    public MongoMemberRepository(SpringDataMongoMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Customer customer) {
        Customer save = repository.save(customer);
        return save.getCustomerId();
    }

    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void update(String customerId, CustomerRequestDto data) {
        repository.findById(customerId).ifPresent(customer -> {
            if (!data.getName().isEmpty()) customer.setName(data.getName());
            if (!data.getEmail().isEmpty()) customer.setEmail(data.getEmail());
            if (!data.getPhoneNumber().isEmpty()) customer.setPhoneNumber(data.getPhoneNumber());
            repository.save(customer);
        });
    }

    @Override
    public void update(String customerId, CustomerRequestDto data, Point location) {
        repository.findById(customerId).ifPresent(customer -> {
            if (!data.getName().isEmpty()) customer.setName(data.getName());
            if (!data.getEmail().isEmpty()) customer.setEmail(data.getEmail());
            if (!data.getPhoneNumber().isEmpty()) customer.setPhoneNumber(data.getPhoneNumber());
            customer.setAddress(data.getAddress());
            customer.setLocation(location);
            if (!data.getAddressDetail().isEmpty()) customer.setAddressDetail(data.getAddressDetail());
            repository.save(customer);
        });
    }


}
