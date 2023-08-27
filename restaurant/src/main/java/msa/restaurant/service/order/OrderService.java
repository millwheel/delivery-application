package msa.restaurant.service.order;

import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Order order) {
        orderRepository.createOrder(order);
    }

    public List<Order> getOrderList(String storeId) {
        return orderRepository.readOrderList(storeId);
    }

    public Optional<Order> getOrder(String orderId) {
        return orderRepository.readOrder(orderId);
    }

    public OrderStatus changeOrderStatus(Order order, OrderStatus status, OrderStatusUpdatePolicy orderStatusUpdatePolicy) {
        orderStatusUpdatePolicy.checkStatusUpdatable(status);

        return orderRepository.updateOrderStatus(order.getOrderId(), status);
    }

    public void assignRiderToOrder(String orderId, OrderStatus orderStatus, RiderPartDto riderPartDto) {
        orderRepository.updateRiderInfo(orderId, orderStatus, riderPartDto);
    }

    public void changeOrderStatusFromOtherServer(String orderId, OrderStatus orderStatus) {
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }

}
