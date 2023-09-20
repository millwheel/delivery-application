package msa.restaurant.repository.order;

import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.exception.OrderMismatchException;
import msa.restaurant.exception.OrderNonexistentException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class MongoOrderRepository implements OrderRepository {

    private final SpringDataMongoOrderRepository repository;

    public MongoOrderRepository(SpringDataMongoOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Order readOrder(String storeId, String orderId) {
        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNonexistentException(orderId));
        if(!Objects.equals(order.getStoreId(), storeId)) throw new OrderMismatchException(orderId, storeId);
        return order;
    }

    @Override
    public List<Order> readOrderList(String storeId) {
        return repository.findByStoreId(storeId);
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus orderStatus) {
        Order order = repository.findById(orderId).orElseThrow(() -> new OrderNonexistentException(orderId));
        order.setOrderStatus(orderStatus);
        return repository.save(order);
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
}
