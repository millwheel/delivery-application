package msa.customer.service;

import msa.customer.entity.Basket;
import msa.customer.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public String createOrder(String customerId, String storeId, String basketId){

    }
}
