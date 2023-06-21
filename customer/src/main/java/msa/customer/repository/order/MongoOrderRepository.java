package msa.customer.repository.order;

import msa.customer.entity.Order;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoOrderRepository implements OrderRepository{

    private final SpringDataMongoOrderRepository repository;

    public MongoOrderRepository(SpringDataMongoOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Order order) {
        Order savedOrder = repository.save(order);
        return savedOrder.getOrderId();
    }

    @Override
    public Optional<Order> findById(String orderId) {
        return repository.findById(orderId);
    }

    @Override
    public void deleteById(String orderId) {
        repository.deleteById(orderId);
    }
}
