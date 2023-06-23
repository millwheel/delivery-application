package msa.customer.controller;

import msa.customer.entity.order.Order;
import msa.customer.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/order/")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public void createOrder(@RequestAttribute String customerId,
                            @PathVariable String storeId){
        Order order = orderService.createOrder(customerId, storeId, customerId);
    }
}
