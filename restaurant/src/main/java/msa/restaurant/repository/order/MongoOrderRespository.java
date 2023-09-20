package msa.restaurant.repository.order;

import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MongoOrderRespository implements OrderRepository {

    private final SpringDataMongoOrderRepository repository;

    public MongoOrderRespository(SpringDataMongoOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public Order createOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Order readOrder(String orderId) {
        return repository.findById(orderId).orElseThrow();
    }

    @Override
    public List<Order> readOrderList(String storeId) {
        return repository.findByStoreId(storeId);
    }

    @Override
    public Order updateOrderStatus(String orderId, OrderStatus orderStatus) {
        Order order = repository.findById(orderId).orElseThrow();
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

    @Override
    public void deleteOrder(String orderId) {
        repository.deleteById(orderId);
    }
}
