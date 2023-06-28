package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.restaurant.dto.order.OrderResponseDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.service.messaging.SendingMessageConverter;
import msa.restaurant.service.messaging.SqsService;
import msa.restaurant.service.order.OrderService;
import msa.restaurant.service.sse.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant/store/{storeId}/order")
public class OrderController {

    private final OrderService orderService;
    private final SseService sseService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

    @Autowired
    public OrderController(OrderService orderService, SseService sseService, SendingMessageConverter sendingMessageConverter, SqsService sqsService) {
        this.orderService = orderService;
        this.sseService = sseService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.sqsService = sqsService;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter showOrderList(@RequestAttribute("cognitoUsername") String managerId,
                                    @PathVariable String storeId){
        SseEmitter sseEmitter = sseService.connect(managerId);
        sseService.showOrderList(storeId);
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

    @PostMapping("/{orderId}")
    public void changeOrderStatus(@PathVariable String orderId,
                                  @PathVariable String storeId,
                                  HttpServletResponse response) throws IOException {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        Order order = orderOptional.get();
        OrderStatus changedOrderStatus = orderService.changeOrderStatusFromClient(orderId, order.getOrderStatus());
        String messageToUpdateOrderStatus = sendingMessageConverter.createMessageToChangeOrderStatus(orderId, changedOrderStatus);
        sqsService.sendToRider(messageToUpdateOrderStatus);
        sqsService.sendToCustomer(messageToUpdateOrderStatus);
        response.sendRedirect("/restaurant/store/" + storeId + "/order");
    }

}
