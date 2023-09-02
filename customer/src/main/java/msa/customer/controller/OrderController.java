package msa.customer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.customer.service.MessageService;
import msa.customer.service.basket.BasketService;
import msa.customer.dto.order.OrderPartResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.service.order.OrderService;
import msa.customer.sse.ServerSentEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/customer/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final BasketService basketService;
    private final MessageService messageService;
    private final ServerSentEvent serverSentEvent;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderPartResponseDto> showOrderList(@RequestAttribute("cognitoUsername") String customerId) {
        return orderService.getOrderList(customerId).stream().map(OrderPartResponseDto::new).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrder(@RequestAttribute("cognitoUsername") String customerId) {
        String orderId = orderService.createOrder(customerId, customerId);
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if(orderOptional.isEmpty()){
            throw new RuntimeException("Can't create order");
        }
        Order order = orderOptional.get();
        messageService.SendOrderMessage(order);
        basketService.deleteAllInBasket(customerId);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showOrderInfo(@RequestAttribute("cognitoUsername") String customerId,
                                    @PathVariable String orderId){
        SseEmitter sseEmitter = serverSentEvent.connect(customerId);
        serverSentEvent.showOrder(customerId, orderId);
        return sseEmitter;
    }
}