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
    public String createOrder(Order order) {
        Order savedOrder = repository.save(order);
        return savedOrder.getOrderId();
    }

    @Override
    public Optional<Order> readOrder(String orderId) {
        return repository.findById(orderId);
    }

    @Override
    public List<Order> readOrderList(String storeId) {
        return repository.findByStoreId(storeId);
    }

    @Override
    public void updateOrderStatus(String orderId, OrderStatus orderStatus) {
        repository.findById(orderId).ifPresent(order -> {
            order.setOrderStatus(orderStatus);
            repository.save(order);
        });
    }

    @Override
    public void updateOrderRiderInfo(String orderId, OrderStatus orderStatus, RiderPartDto riderPartDto) {
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
