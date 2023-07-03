package msa.rider.repository.order;


import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataMongoOrderRepository extends MongoRepository<Order, String> {
    List<Order> findByStoreLocationNearAndOrderStatusIs(Point location, OrderStatus orderStatus);
    List<Order> findByRiderId(String riderId);
}
