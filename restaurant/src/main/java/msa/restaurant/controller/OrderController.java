package msa.restaurant.controller;

import msa.restaurant.dto.order.OrderPartResponseDto;
import msa.restaurant.dto.order.OrderResponseDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.service.order.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant/store/{storeId}/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderPartResponseDto> showOrderList(@RequestAttribute("cognitoUsername") String managerId){
        List<Order> orderList = orderService.getOrderList(managerId).orElseGet(ArrayList::new);
        List<OrderPartResponseDto> orderPartInfoList = new ArrayList<>();
        orderList.forEach(order -> {
            orderPartInfoList.add(new OrderPartResponseDto(order));
        });
        return orderPartInfoList;
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
