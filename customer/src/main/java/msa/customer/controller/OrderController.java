package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.dto.order.OrderPartResponseDto;
import msa.customer.dto.order.OrderResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/customer/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderPartResponseDto> showOrderList(@RequestAttribute String customerId) {
        Optional<List<Order>> orderListOptional = orderService.getOrderList(customerId);
        if (orderListOptional.isEmpty()){
            throw new RuntimeException("No order info");
        }
        List<OrderPartResponseDto> orderPartInfoList = new ArrayList<>();
        List<Order> orderList = orderListOptional.get();
        orderList.forEach(order -> {
            orderPartInfoList.add(new OrderPartResponseDto(order));
        });
        return orderPartInfoList;
    }

    @PostMapping
    public void createOrder(@RequestAttribute String customerId,
                            HttpServletResponse response) throws IOException {
        orderService.createOrder(customerId, customerId);
        response.sendRedirect("/customer/order");
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto showOrderInfo(@RequestAttribute String customerId,
                                          @PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        Order order = orderOptional.get();
        return new OrderResponseDto(order);
    }
}