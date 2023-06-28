package msa.customer.repository.order;

import msa.customer.dto.rider.RiderSqsDto;
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
    public String createOrder(Order order) {
        Order savedOrder = repository.save(order);
        return savedOrder.getOrderId();
    }

    @Override
    public Optional<Order> readOrder(String orderId) {
        return repository.findById(orderId);
    }

    @Override
    public Optional<List<Order>> readOrderList(String customerId) {
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
    public void updateRiderInfo(String orderId, RiderSqsDto riderSqsDto) {
        repository.findById(orderId).ifPresent(order -> {
            order.setRiderId(riderSqsDto.getRiderId());
            order.setRiderName(riderSqsDto.getName());
            order.setRiderPhoneNumber(riderSqsDto.getPhoneNumber());
        });
    }

    @Override
    public void deleteOrder(String orderId) {
        repository.deleteById(orderId);
    }
}
