package msa.restaurant.repository.order;

import msa.restaurant.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    String createOrder(Order order);
    Optional<Order> readOrder(String orderId);
    Optional<List<Order>> readOrderList(String storeId);
    void deleteOrder(String orderId);
}
