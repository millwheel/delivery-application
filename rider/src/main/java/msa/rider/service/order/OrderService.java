package msa.rider.service.order;

import lombok.AllArgsConstructor;
import msa.rider.dto.rider.RiderSqsDto;
import msa.rider.entity.member.Rider;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.exception.OrderStatusUnchangeableException;
import msa.rider.repository.member.MemberRepository;
import msa.rider.repository.order.OrderRepository;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;

    public void createOrder(Order order){
        orderRepository.createOrder(order);
    }

    public List<Order> getNewOrderListNearRider(String riderId){
        Rider rider = memberRepository.readRider(riderId);
        Point location = rider.getLocation();
        return orderRepository.findNewOrderListNearLocation(location);
    }

    public Order getOrder(String orderId){
        return orderRepository.readOrder(orderId);
    }

    public List<Order> getOrderListOfRider(String riderId){
        return orderRepository.findByRiderId(riderId);
    }

    public Order assignRider(String orderId, RiderSqsDto riderSqsDto) {
        return orderRepository.updateOrderRiderInfo(orderId, riderSqsDto);
    }

    public Order changeOrderStatusFromClient(String orderId, String riderId){
        return orderRepository.updateOrderStatus(orderId, riderId);
    }

    public void changeOrderStatusFromOtherServer(String orderId, OrderStatus orderStatus){
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }
}
