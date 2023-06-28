package msa.rider.repository.order;


import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    String createOrder(Order order);
    Optional<Order> readOrder(String orderId);
    List<Order> readOrderListNearLocation(Point location);
    void updateOrderStatus(String orderId, OrderStatus orderStatus);
    void deleteOrder(String orderId);
}
