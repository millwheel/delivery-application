package msa.customer.repository.member;

import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.Customer;
import org.springframework.data.geo.Point;

import java.util.Optional;

public interface MemberRepository {
    String create(Customer customer);
    Optional<Customer> findById(String id);
    void update(String customerId, CustomerRequestDto data, Point location);

}
