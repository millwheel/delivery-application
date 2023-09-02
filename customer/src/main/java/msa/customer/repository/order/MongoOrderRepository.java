package msa.customer.repository.order;

import msa.customer.dto.rider.RiderPartDto;
import msa.customer.entity.order.Order;
import msa.customer.entity.order.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<Order> readOrder(String orderId) {
        return repository.findById(orderId);
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
