package msa.rider.service.order;

import msa.rider.dto.rider.RiderPartDto;
import msa.rider.entity.member.Rider;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.repository.member.MemberRepository;
import msa.rider.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
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

    public List<Order> getOrderListNearRider(String riderId){
        Rider rider = memberRepository.findById(riderId).get();
        Point location = rider.getLocation();
        return orderRepository.findNewOrderListNearLocation(location);
    }

    public Optional<Order> getOrder(String orderId){
        return orderRepository.findById(orderId);
    }

    public List<Order> getOrderListOfRider(String riderId){
        return orderRepository.findByRiderId(riderId);
    }

    public RiderPartDto updateRiderInfo(String riderId, Order order){
        if (!order.getOrderStatus().equals(OrderStatus.ORDER_ACCEPT)){
            throw new IllegalStateException("The current order status is not changeable.");
        }
        Rider rider = memberRepository.findById(riderId).get();
        RiderPartDto riderPartDto = new RiderPartDto(rider);
        orderRepository.updateOrderRiderInfo(order.getOrderId(), riderPartDto, rider.getLocation(), OrderStatus.RIDER_ASSIGNED);
        return riderPartDto;
    }

    public OrderStatus changeOrderStatusFromClient(String orderId, OrderStatus orderStatus){
        if (orderStatus.equals(OrderStatus.FOOD_READY) || orderStatus.equals(OrderStatus.RIDER_ASSIGNED)) {
            orderRepository.updateOrderStatus(orderId, OrderStatus.DELIVERY_IN_PROGRESS);
            return OrderStatus.DELIVERY_IN_PROGRESS;
        } else if (orderStatus.equals(OrderStatus.DELIVERY_IN_PROGRESS)) {
            orderRepository.updateOrderStatus(orderId, OrderStatus.DELIVERY_COMPLETE);
            return OrderStatus.DELIVERY_COMPLETE;
        } else {
            throw new IllegalStateException("The current order status is not changeable.");
        }
    }

    public void changeOrderStatusFromOtherServer(String orderId, OrderStatus orderStatus){
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }
}
