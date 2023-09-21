package msa.rider.repository.order;

import lombok.AllArgsConstructor;
import msa.rider.dto.rider.RiderSqsDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.exception.OrderMismatchException;
import msa.rider.exception.OrderNonexistentException;
import msa.rider.exception.OrderStatusUnchangeableException;
import msa.rider.exception.RiderNonexistentException;
import msa.rider.policy.OrderRiderAssignPolicy;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class MongoOrderRepository implements OrderRepository {

    private final SpringDataMongoOrderRepository repository;
    private final OrderRiderAssignPolicy orderRiderAssignPolicy;

    @Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Order readOrder(String orderId) {
        return repository.findById(orderId).orElseThrow(() -> new OrderNonexistentException(orderId));
    }

    @Override
    public List<Order> findByRiderId(String riderId) {
        return repository.findByRiderId(riderId);
    }

    @Override
    public List<Order> findNewOrderListNearLocation(Point location) {
        return repository.findByStoreLocationNearAndOrderStatusIs(location, OrderStatus.ORDER_ACCEPT);
    }

    @Override
    public Order updateOrderStatus(String orderId, String riderId) {
        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNonexistentException(orderId));
        if (!Objects.equals(order.getRiderId(), riderId)) throw new OrderMismatchException(orderId, riderId);
        OrderStatus prevOrderStatus = order.getOrderStatus();
        OrderStatus nextStatus = extractNextStatus(prevOrderStatus);
        order.setOrderStatus(nextStatus);
        return repository.save(order);
    }

    private OrderStatus extractNextStatus(OrderStatus orderStatus){
        if (orderStatus.equals(OrderStatus.FOOD_READY) || orderStatus.equals(OrderStatus.RIDER_ASSIGNED)) {
            return OrderStatus.DELIVERY_IN_PROGRESS;
        } else if (orderStatus.equals(OrderStatus.DELIVERY_IN_PROGRESS)) {
            return OrderStatus.DELIVERY_COMPLETE;
        }else {
            throw new OrderStatusUnchangeableException(orderStatus);
        }
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus orderStatus) {
        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNonexistentException(orderId));
        order.setOrderStatus(orderStatus);
        return repository.save(order);
    }

    @Override
    public Order updateOrderRiderInfo(String orderId, RiderSqsDto riderSqsDto) {
        Order order = repository.findById(orderId).orElseThrow(() -> new RiderNonexistentException(orderId));
        orderRiderAssignPolicy.checkStatusAssignable(order.getOrderStatus());
        order.setRiderId(riderSqsDto.getRiderId());
        order.setRiderName(riderSqsDto.getName());
        order.setRiderPhoneNumber(riderSqsDto.getPhoneNumber());
        order.setRiderLocation(riderSqsDto.getRiderLocation());
        order.setOrderStatus(OrderStatus.RIDER_ASSIGNED);
        return repository.save(order);
    }
}
