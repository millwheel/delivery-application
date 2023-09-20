package msa.restaurant.controller;

import lombok.AllArgsConstructor;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.message_queue.SendingMessageConverter;
import msa.restaurant.message_queue.SqsService;
import msa.restaurant.service.order.OrderService;
import msa.restaurant.sse.SseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/restaurant/store/{storeId}/order")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final SseService sseService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;


    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showOrderList(@PathVariable String storeId){
        SseEmitter sseEmitter = sseService.connectForList(storeId);
        sseService.showOrderList(storeId);
        return sseEmitter;
    }

    @GetMapping(path="/{orderId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showOrderInfo(@PathVariable String storeId,
                                    @PathVariable String orderId,
                                    @RequestAttribute("order") Order order){
        SseEmitter sseEmitter = sseService.connectForInfo(storeId);
        sseService.showOrderInfo(storeId, orderId);
        return sseEmitter;
    }

    @PostMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void acceptOrder(@PathVariable String orderId,
                            @RequestAttribute("order") Order order) {
        OrderStatus changedOrderStatus = orderService.changeOrderStatusToOrderAccept(orderId, order.getOrderStatus());
        order.setOrderStatus(changedOrderStatus);
        String messageToAcceptOrder = sendingMessageConverter.createMessageToAcceptOrder(order);
        String messageToRequestOrder = sendingMessageConverter.createMessageToRequestOrder(order);
        sqsService.sendToCustomer(messageToAcceptOrder);
        sqsService.sendToRider(messageToRequestOrder);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void changeOrderStatus(@PathVariable String orderId,
                                  @RequestAttribute("order") Order order){
        OrderStatus changedOrderStatus = orderService.changeOrderStatusToFoodReady(orderId, order.getOrderStatus());
        order.setOrderStatus(changedOrderStatus);
        String messageToChangeOrderStatus = sendingMessageConverter.createMessageToChangeOrderStatus(order);
        sqsService.sendToCustomer(messageToChangeOrderStatus);
        sqsService.sendToRider(messageToChangeOrderStatus);
    }

}
