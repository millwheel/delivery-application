package msa.rider.repository.order;


import msa.rider.entity.order.Order;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoOrderRepository extends MongoRepository<Order, String> {
    List<Order> findByLocationNear(Point location);
}