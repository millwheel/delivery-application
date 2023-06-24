package msa.customer.repository.member;

import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DynamoMemberRepository implements MemberRepository {

    private final SpringDataDynamoMemberRepository repository;

    public DynamoMemberRepository(SpringDataDynamoMemberRepository repository) {
        this.repository = repository;
    }
    @Override
    public String create(Customer customer) {
        Customer savedCustomer = repository.save(customer);
        return savedCustomer.getCustomerId();
    }

    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void update(String customerId, CustomerRequestDto data) {

    }

    @Override
    public void update(String customerId, CustomerRequestDto data, Point location) {

    }

}
