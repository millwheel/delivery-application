package msa.customer.repository.order;

import msa.customer.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    String createOrder(Order order);
    Optional<Order> readOrder(String orderId);
    Optional<List<Order>> readOrderList(String customerId);
    void deleteOrder(String orderId);
}
