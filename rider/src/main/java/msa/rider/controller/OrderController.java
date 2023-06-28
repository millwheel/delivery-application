package msa.rider.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.rider.dto.order.OrderPartResponseDto;
import msa.rider.dto.order.OrderResponseDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.service.messaging.SendingMessageConverter;
import msa.rider.service.messaging.SqsService;
import msa.rider.service.order.OrderService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequestMapping("/rider/order")
@RestController
public class OrderController {
    private final OrderService orderService;
    private final SqsService sseService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

    public OrderController(OrderService orderService, SqsService sseService, SendingMessageConverter sendingMessageConverter, SqsService sqsService) {
        this.orderService = orderService;
        this.sseService = sseService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.sqsService = sqsService;
    }


    @GetMapping("/{orderId}")
    public OrderResponseDto showOrderInfo(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        return new OrderResponseDto(orderOptional.get());
    }

    @PostMapping("/{orderId}")
    public void changeOrderStatus(@PathVariable String orderId,
                                  HttpServletResponse response) throws IOException {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        Order order = orderOptional.get();
        OrderStatus changedOrderStatus = orderService.changeOrderStatusFromClient(orderId, order.getOrderStatus());
        String messageToUpdateOrderStatus = sendingMessageConverter.createMessageToChangeOrderStatus(orderId, changedOrderStatus);
        sqsService.sendToRestaurant(messageToUpdateOrderStatus);
        sqsService.sendToCustomer(messageToUpdateOrderStatus);
        response.sendRedirect("/restaurant/order" + orderId);
    }

}
