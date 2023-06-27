package msa.restaurant.repository.order;

import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    String createOrder(Order order);
    Optional<Order> readOrder(String orderId);
    List<Order> readOrderList(String storeId);
    void updateOrderStatus(String orderId, OrderStatus orderStatus);
    void deleteOrder(String orderId);
}
