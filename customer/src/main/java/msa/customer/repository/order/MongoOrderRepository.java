package msa.customer.repository.order;

import msa.customer.entity.order.Order;
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
    public void deleteOrder(String orderId) {
        repository.deleteById(orderId);
    }
}
