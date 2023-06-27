package msa.restaurant.service.order;

import msa.restaurant.entity.order.Order;
import msa.restaurant.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createOrder(Order order){
        orderRepository.createOrder(order);
    }

    public Optional<List<Order>> getOrderList(String storeId){
        return orderRepository.readOrderList(storeId);
    }

    public Optional<Order> getOrder(String orderId){
        return orderRepository.readOrder(orderId);
    }
}
