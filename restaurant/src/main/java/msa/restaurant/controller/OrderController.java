package msa.restaurant.controller;

import msa.restaurant.dto.order.OrderPartResponseDto;
import msa.restaurant.dto.order.OrderResponseDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.service.order.OrderService;
import msa.restaurant.service.sse.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant/store/{storeId}/order")
public class OrderController {

    private final OrderService orderService;
    private final SseService sseService;

    @Autowired
    public OrderController(OrderService orderService, SseService sseService) {
        this.orderService = orderService;
        this.sseService = sseService;
    }

    @GetMapping
    public SseEmitter showOrderList(@RequestAttribute("cognitoUsername") String managerId){
        SseEmitter sseEmitter = sseService.connect(managerId);
        sseService.showOrderList(managerId);
        return sseEmitter;
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto showOrderInfo(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        return new OrderResponseDto(orderOptional.get());
    }
}
