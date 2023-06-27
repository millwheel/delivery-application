package msa.restaurant.repository.order;


import msa.restaurant.entity.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoOrderRepository extends MongoRepository<Order, String> {
    Optional<List<Order>> findByStoreId(String storeId);
}
