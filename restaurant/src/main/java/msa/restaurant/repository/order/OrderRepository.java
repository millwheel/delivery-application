package msa.restaurant.repository.order;

import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;

import java.util.List;

public interface OrderRepository {
    Order createOrder(Order order);
    Order readOrder(String storeId, String orderId);
    List<Order> readOrderList(String storeId);
    Order updateOrderStatus(String orderId, OrderStatus orderStatus);
    void updateRiderInfo(String orderId, OrderStatus orderStatus, RiderPartDto riderPartDto);
}
