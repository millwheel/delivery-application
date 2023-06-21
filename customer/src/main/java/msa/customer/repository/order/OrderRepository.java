package msa.customer.repository.order;

import msa.customer.entity.Order;

import java.util.Optional;

public interface OrderRepository {
    String create(Order order);
    Optional<Order> findById(String orderId);
    void deleteById(String orderId);
}
