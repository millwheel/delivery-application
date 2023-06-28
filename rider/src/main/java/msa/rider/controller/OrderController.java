package msa.rider.controller;

import msa.rider.service.messaging.SendingMessageConverter;
import msa.rider.service.messaging.SqsService;
import msa.rider.service.order.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
