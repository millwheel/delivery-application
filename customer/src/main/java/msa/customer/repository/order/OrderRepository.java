package msa.customer.repository.order;

import msa.customer.dto.rider.RiderPartDto;
import msa.customer.entity.order.Order;
import msa.customer.entity.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    String createOrder(Order order);
    Optional<Order> readOrder(String orderId);
    Optional<List<Order>> readOrderList(String customerId);
    void updateOrderStatus(String orderId, OrderStatus orderStatus);
    void updateRiderInfo(String orderId, OrderStatus orderStatus, RiderPartDto riderPartDto);
    void deleteOrder(String orderId);
}
