package msa.customer.repository.member;

import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import org.springframework.data.geo.Point;

import java.util.Optional;

public interface MemberRepository {
    String createMember(Customer customer);
    Optional<Customer> readMember(String id);
    void updateMember(String customerId, CustomerRequestDto data);
    void updateMember(String customerId, CustomerRequestDto data, Point location);

}
