package msa.customer.repository.member;

import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import org.springframework.data.geo.Point;

import java.util.Optional;

public interface MemberRepository {
    String create(Customer customer);
    Optional<Customer> findById(String id);
    Customer update(String customerId, CustomerRequestDto data);
    Customer update(String customerId, CustomerRequestDto data, Point location);

}
