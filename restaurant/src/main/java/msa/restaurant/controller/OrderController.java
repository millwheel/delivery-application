package msa.restaurant.controller;

import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.sqs.SendingMessageConverter;
import msa.restaurant.sqs.SqsService;
import msa.restaurant.service.order.OrderService;
import msa.restaurant.sse.OrderInfoSseService;
import msa.restaurant.sse.OrderListSseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/restaurant/store/{storeId}/order")
public class OrderController {

    private final OrderService orderService;
    private final OrderListSseService orderListSseService;
    private final OrderInfoSseService orderInfoSseService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

    @Autowired
    public OrderController(OrderService orderService, OrderListSseService orderListSseService, OrderInfoSseService orderInfoSseService, SendingMessageConverter sendingMessageConverter, SqsService sqsService) {
        this.orderService = orderService;
        this.orderListSseService = orderListSseService;
        this.orderInfoSseService = orderInfoSseService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.sqsService = sqsService;
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showOrderList(@PathVariable String storeId){
        SseEmitter sseEmitter = orderListSseService.connect(storeId);
        orderListSseService.showOrderList(storeId);
        return sseEmitter;
    }

    @GetMapping(path="/{orderId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showOrderInfo(@PathVariable String storeId,
                                    @PathVariable String orderId,
                                    @RequestAttribute("order") Order order){
        SseEmitter sseEmitter = orderInfoSseService.connect(storeId);
        orderInfoSseService.showOrderInfo(storeId, orderId);
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
