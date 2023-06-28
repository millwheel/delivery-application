package msa.customer.repository.order;

import msa.customer.dto.rider.RiderSqsDto;
import msa.customer.entity.order.Order;
import msa.customer.entity.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    String createOrder(Order order);
    Optional<Order> readOrder(String orderId);
    Optional<List<Order>> readOrderList(String customerId);
    void updateOrderStatus(String orderId, OrderStatus orderStatus);
    void updateRiderInfo(String orderId, RiderSqsDto riderSqsDto);
    void deleteOrder(String orderId);
}
