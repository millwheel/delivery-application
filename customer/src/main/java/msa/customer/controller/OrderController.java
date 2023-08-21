package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.customer.service.basket.BasketService;
import msa.customer.sqs.ReceivingMessageConverter;
import msa.customer.sqs.SendingMessageConverter;
import msa.customer.dto.order.OrderPartResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.service.order.OrderService;
import msa.customer.sqs.SqsService;
import msa.customer.sse.SseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/customer/order")
public class OrderController {

    private final OrderService orderService;
    private final BasketService basketService;

    /*
        (C) OrderController가 관심있는 부분은 주문 정보를 가져오고 처리하는 것이지 특정 서비스(SQS, SSE)를 사용하는 부분은 아닌 것 같습니다.
        MessageService / OrderService / StoreService 등으로 Service를 분리하여서 그 안에서 SsqService, SseService 및 converter를 조합하여
        필요한 데이터를 반환하는 것이 관심사의 분리가 더 적절하게 이루어 질 것 같습니다.
    */
    private final SqsService sqsService;
    private final SseService sseService;
    private final SendingMessageConverter sendingMessageConverter;
    private final ReceivingMessageConverter receivingMessageConverter; // (C) 사용하지 않는 의존성은 지워주는 것이 좋을 것 같습니다.

    /* (C) 여기도 lombok의 RequiredArgsConstructor를 사용하는 것이 좋아보입니다. */
    public OrderController(OrderService orderService,
                           BasketService basketService,
                           SqsService sqsService,
                           SseService sseService,
                           SendingMessageConverter sendingMessageConverter,
                           ReceivingMessageConverter receivingMessageConverter) {
        this.orderService = orderService;
        this.basketService = basketService;
        this.sqsService = sqsService;
        this.sseService = sseService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.receivingMessageConverter = receivingMessageConverter;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderPartResponseDto> showOrderList(@RequestAttribute("cognitoUsername") String customerId) {

        /*
            (C) StreamAPI를 사용하면 아래처럼 처리할 수 있어 보입니다.
            + getOrderList는 Optional이 아닌 List<Order> 타입을 반환하면 좋을 것 같습니다
        */
        return orderService.getOrderList(customerId)
            .orElseThrow()
            .stream()
            .map(OrderPartResponseDto::new)
            .collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    /*
        (C) controller에서 하는 일이 꽤 많은 것 같습니다. 서비스로 로직을 빼보시는 것을 추천드립니다.
    */
    public void createOrder(@RequestAttribute("cognitoUsername") String customerId,
                            HttpServletResponse response) throws IOException {
        /*
            1. (Q) 여기는 customerId가 왜 basketId로 사용되고 있나요?
            2. (C) createOrder로 order를 생성했다면, orderId보다는 order 자체를 반환하는 것이 사용성 측면에서 좋을 것 같습니다.
        */
        String orderId = orderService.createOrder(customerId, customerId);
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()) {
            throw new RuntimeException("Can't create order");
        }
        Order order = orderOptional.get();
        String messageToCreateOrder = sendingMessageConverter.createMessageToCreateOrder(order);
        sqsService.sendToRestaurant(messageToCreateOrder);
        basketService.deleteAllInBasket(customerId);
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showOrderInfo(@RequestAttribute("cognitoUsername") String customerId,
                                    @PathVariable String orderId) {
        SseEmitter sseEmitter = sseService.connect(customerId);
        sseService.showOrder(customerId, orderId);
        return sseEmitter;
    }

}