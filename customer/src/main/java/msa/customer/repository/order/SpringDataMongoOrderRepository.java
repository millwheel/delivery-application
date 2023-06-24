package msa.customer.repository.order;

import msa.customer.entity.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoOrderRepository extends MongoRepository<Order, String> {
    Optional<List<Order>> findByCustomerId(String customerId);
}
