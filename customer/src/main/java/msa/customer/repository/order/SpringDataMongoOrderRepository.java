package msa.customer.repository.order;

import msa.customer.entity.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoOrderRepository extends MongoRepository<Order, String> {
}
