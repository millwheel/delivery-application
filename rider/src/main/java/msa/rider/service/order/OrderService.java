package msa.rider.service.order;

import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.repository.member.MemberRepository;
import msa.rider.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, MemberRepository memberRepository) {
        this.orderRepository = orderRepository;
        this.memberRepository = memberRepository;
    }

    public void createOrder(Order order){
        orderRepository.createOrder(order);
    }

    public List<Order> getOrderListNearRider(String rider){
        memberRepository.findById()
        return orderRepository.findNewOrderListNearLocation(location);
    }

    public Optional<Order> getOrder(String orderId){
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrderListOfRider(String riderId){
        return orderRepository.findByRiderId(riderId);
    }

    public OrderStatus changeOrderStatusFromClient(String orderId, OrderStatus orderStatus){
        if(orderStatus.equals(OrderStatus.ORDER_ACCEPT)){
            orderRepository.updateOrderStatus(orderId, OrderStatus.RIDER_ASSIGNED);
            return OrderStatus.RIDER_ASSIGNED;
        } else if (orderStatus.equals(OrderStatus.FOOD_READY)) {
            orderRepository.updateOrderStatus(orderId, OrderStatus.DELIVERY_IN_PROGRESS);
            return OrderStatus.DELIVERY_IN_PROGRESS;
        } else if (orderStatus.equals(OrderStatus.DELIVERY_IN_PROGRESS)) {
            orderRepository.updateOrderStatus(orderId, OrderStatus.DELIVERY_COMPLETE);
            return OrderStatus.DELIVERY_COMPLETE;
        } else {
            throw new RuntimeException("The current order status is not changeable");
        }
    }

    public void changeOrderStatusFromOtherServer(String orderId, OrderStatus orderStatus){
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }
}
