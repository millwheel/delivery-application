package msa.customer.repository.member;

import msa.customer.dto.customer.CustomerRequestDto;
import msa.customer.entity.member.Customer;
import org.springframework.data.geo.Point;

import java.util.Optional;

public interface MemberRepository {
    String createCustomer(Customer customer);
    Boolean checkCustomer(String customerId);
    Customer readCustomer(String customerId);
    Customer updateCustomer(String customerId, CustomerRequestDto data);
    Customer updateCustomer(String customerId, CustomerRequestDto data, Point location);

}
