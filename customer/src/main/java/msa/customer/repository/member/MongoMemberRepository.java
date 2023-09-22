package msa.customer.repository.member;

import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import msa.customer.exception.MemberNonexistentException;
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
    public String createCustomer(Customer customer) {
        Customer save = repository.save(customer);
        return save.getCustomerId();
    }

    @Override
    public Boolean checkCustomer(String customerId) {
        return repository.existsById(customerId);
    }

    @Override
    public Customer readCustomer(String customerId) {
        return repository.findById(customerId).orElseThrow(() -> new MemberNonexistentException(customerId));
    }

    @Override
    public Customer updateCustomer(String customerId, CustomerRequestDto data) {
        Customer customer = repository.findById(customerId).orElseThrow(() -> new MemberNonexistentException(customerId));
        if (data.getName() != null) customer.setName(data.getName());
        if (data.getPhoneNumber() != null) customer.setPhoneNumber(data.getPhoneNumber());
        return repository.save(customer);
    }

    @Override
    public Customer updateCustomer(String customerId, CustomerRequestDto data, Point location) {
        Customer customer = repository.findById(customerId).orElseThrow(() -> new MemberNonexistentException(customerId));
        if (data.getName() != null) customer.setName(data.getName());
        if (data.getPhoneNumber() != null) customer.setPhoneNumber(data.getPhoneNumber());
        customer.setAddress(data.getAddress());
        customer.setLocation(location);
        if (data.getAddressDetail() != null) customer.setAddressDetail(data.getAddressDetail());
        return repository.save(customer);
    }
}
