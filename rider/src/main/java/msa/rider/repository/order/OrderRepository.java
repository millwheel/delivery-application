package msa.rider.repository.order;


import msa.rider.dto.rider.RiderSqsDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import org.springframework.data.geo.Point;

import java.util.List;

public interface OrderRepository {
    Order createOrder(Order order);
    Order readOrder(String orderId);
    List<Order> findByRiderId(String riderId);
    List<Order> findNewOrderListNearLocation(Point location);
    Order updateOrderStatus(String orderId, String riderId);
    Order updateOrderStatus(String orderId, OrderStatus orderStatus);
    Order updateOrderRiderInfo(String orderId, RiderSqsDto riderSqsDto);
}
