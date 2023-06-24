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

    @GetMapping("/list")
    public List<OrderPartResponseDto> showOrderList(@RequestAttribute String customerId,
                                                    @PathVariable String storeId) {
        Optional<List<Order>> orderListOptional = orderService.getOrderList(customerId);
        if (orderListOptional.isEmpty()){
            throw new RuntimeException("No order info");
        }
        List<OrderPartResponseDto> orderPartInfoList = new ArrayList<>();
        List<Order> orderList = orderListOptional.get();
        for (Order order : orderList) {
            orderPartInfoList.add(new OrderPartResponseDto(order));
        }
        return orderPartInfoList;
    }

    @PostMapping("/add")
    public void createOrder(@RequestAttribute String customerId,
                            @PathVariable String storeId,
                            HttpServletResponse response) throws IOException {
        orderService.createOrder(customerId, storeId, customerId);
        response.sendRedirect("/customer/order/list");
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