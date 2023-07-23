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

    public void createOrder(Order order){
        orderRepository.createOrder(order);
    }

    public List<Order> getOrderList(String storeId){
        return orderRepository.readOrderList(storeId);
    }

    public Optional<Order> getOrder(String orderId){
        return orderRepository.readOrder(orderId);
    }

    public OrderStatus changeOrderStatusFromClient(String orderId, OrderStatus orderStatus){
        if(orderStatus.equals(OrderStatus.ORDER_REQUEST)){
            orderRepository.updateOrderStatus(orderId, OrderStatus.ORDER_ACCEPT);
            return OrderStatus.ORDER_ACCEPT;
        } else if (orderStatus.equals(OrderStatus.RIDER_ASSIGNED)) {
            orderRepository.updateOrderStatus(orderId, OrderStatus.FOOD_READY);
            return OrderStatus.FOOD_READY;
        } else {
            throw new RuntimeException("The current order status is not changeable");
        }
    }

    public void assignRiderToOrder(String orderId, OrderStatus orderStatus, RiderPartDto riderPartDto){
        orderRepository.updateRiderInfo(orderId, orderStatus, riderPartDto);
    }

    public void changeOrderStatusFromOtherServer(String orderId, OrderStatus orderStatus){
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }
}
