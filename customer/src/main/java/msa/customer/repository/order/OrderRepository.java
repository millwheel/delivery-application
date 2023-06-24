package msa.customer.repository.order;

import msa.customer.entity.order.Order;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    String create(Order order);
    Optional<Order> findById(String orderId);
    Optional<List<Order>> findByCustomerId(String customerId);
    void deleteById(String orderId);
}
