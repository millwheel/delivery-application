package msa.customer.controller;

import msa.customer.dto.order.OrderResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public void showOrderList(@RequestAttribute String customerId,
                              @PathVariable String storeId) {
        orderService.
    }

    @PostMapping("/add")
    public void createOrder(@RequestAttribute String customerId,
                            @PathVariable String storeId) {
        Order order = orderService.createOrder(customerId, storeId, customerId);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto showOrderInfo(@RequestAttribute String customerId,
                                          @PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        Order order = orderOptional.get();

    }
}