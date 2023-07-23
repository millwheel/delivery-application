package msa.rider.repository.order;


import msa.rider.dto.rider.RiderPartDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    String createOrder(Order order);
    Optional<Order> findById(String orderId);
    List<Order> findByRiderId(String riderId);
    List<Order> findNewOrderListNearLocation(Point location);
    void updateOrderStatus(String orderId, OrderStatus orderStatus);
    void updateOrderRiderInfo(String orderId, RiderPartDto riderPartDto, Point location);
    void deleteOrder(String orderId);
}
