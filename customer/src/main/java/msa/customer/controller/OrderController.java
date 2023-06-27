package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.customer.service.messaging.ReceivingMessageConverter;
import msa.customer.service.messaging.SendingMessageConverter;
import msa.customer.dto.order.OrderPartResponseDto;
import msa.customer.dto.order.OrderResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.service.order.OrderService;
import msa.customer.service.messaging.SqsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/customer/order")
public class OrderController {

    private final OrderService orderService;
    private final SqsService sqsService;
    private final SendingMessageConverter sendingMessageConverter;
    private final ReceivingMessageConverter receivingMessageConverter;

    public OrderController(OrderService orderService, SqsService sqsService, SendingMessageConverter sendingMessageConverter, ReceivingMessageConverter receivingMessageConverter) {
        this.orderService = orderService;
        this.sqsService = sqsService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.receivingMessageConverter = receivingMessageConverter;
    }

    @GetMapping
    public List<OrderPartResponseDto> showOrderList(@RequestAttribute("cognitoUsername") String customerId) {
        List<Order> orderList = orderService.getOrderList(customerId).orElseGet(ArrayList::new);
        List<OrderPartResponseDto> orderPartInfoList = new ArrayList<>();
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
        String messageToCreateOrder = sendingMessageConverter.createMessageToCreateOrder(order);
        sqsService.sendToRestaurant(messageToCreateOrder);
        response.sendRedirect("/customer/order");
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