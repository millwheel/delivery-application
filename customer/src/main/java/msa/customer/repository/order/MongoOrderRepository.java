package msa.customer.repository.order;

import msa.customer.dto.rider.RiderPartDto;
import msa.customer.entity.order.Order;
import msa.customer.entity.order.OrderStatus;
import msa.customer.exception.OrderMismatchException;
import msa.customer.exception.OrderNonexistentException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MongoOrderRepository implements OrderRepository{

    private final SpringDataMongoOrderRepository repository;

    public MongoOrderRepository(SpringDataMongoOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Order readOrder(String customerId, String orderId) {
        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNonexistentException(orderId));
        if (!Objects.equals(order.getCustomerId(), customerId)) throw new OrderMismatchException(orderId, customerId);
        return order;
    }

    @Override
    public List<Order> readOrderList(String customerId) {
        return repository.findByCustomerId(customerId);
    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus orderStatus) {
        repository.findById(orderId).ifPresent(order -> {
            order.setOrderStatus(orderStatus);
            repository.save(order);
        });
    }

    @Override
    public void updateRiderInfo(String orderId, OrderStatus orderStatus, RiderPartDto riderPartDto) {
        repository.findById(orderId).ifPresent(order -> {
            order.setOrderStatus(orderStatus);
            order.setRiderId(riderPartDto.getRiderId());
            order.setRiderName(riderPartDto.getName());
            order.setRiderPhoneNumber(riderPartDto.getPhoneNumber());
            repository.save(order);
        });
    }

    @Override
    public void deleteOrder(String orderId) {
        repository.deleteById(orderId);
    }
}
