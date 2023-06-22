package msa.customer.repository.member;

import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.data.geo.Point;
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
    public String create(Customer customer) {
        Customer save = repository.save(customer);
        return save.getCustomerId();
    }

    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void update(String customerId, CustomerRequestDto data, Point location) {
        repository.findById(customerId).ifPresent(customer -> {
            customer.setName(data.getName());
            customer.setEmail(data.getEmail());
            customer.setPhoneNumber(data.getPhoneNumber());
            customer.setAddress(data.getAddress());
            customer.setAddressDetail(data.getAddressDetail());
            customer.setLocation(location);
        });
    }


}
