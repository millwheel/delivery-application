package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.converter.MessageConverter;
import msa.customer.dto.order.OrderPartResponseDto;
import msa.customer.dto.order.OrderResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.service.OrderService;
import msa.customer.service.SqsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer/order")
public class OrderController {

    private final OrderService orderService;
    private final SqsService sqsService;
    private final MessageConverter messageConverter;

    public OrderController(OrderService orderService, SqsService sqsService, MessageConverter messageConverter) {
        this.orderService = orderService;
        this.sqsService = sqsService;
        this.messageConverter = messageConverter;
    }

    @GetMapping
    public List<OrderPartResponseDto> showOrderList(@RequestAttribute("cognitoUsername") String customerId) {
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
    public void createOrder(@RequestAttribute("cognitoUsername") String customerId,
                            HttpServletResponse response) throws IOException {
        String orderId = orderService.createOrder(customerId, customerId);
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if(orderOptional.isEmpty()){
            throw new RuntimeException("Can't create order");
        }
        Order order = orderOptional.get();
        String messageToCreateOrder = messageConverter.createMessageToCreateOrder(order);
        sqsService.sendToRestaurant(messageToCreateOrder);
        response.sendRedirect("/customer/order");
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto showOrderInfo(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        Order order = orderOptional.get();
        return new OrderResponseDto(order);
    }
}