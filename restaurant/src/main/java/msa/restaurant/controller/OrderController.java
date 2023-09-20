package msa.restaurant.controller;

import lombok.AllArgsConstructor;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
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
                                    @PathVariable String orderId){
        SseEmitter sseEmitter = sseService.connectForInfo(storeId);
        sseService.showOrderInfo(storeId, orderId);
        return sseEmitter;
    }

    @PostMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderStatus acceptOrder(@PathVariable String storeId,
                                   @PathVariable String orderId) {
        Order savedOrder = orderService.changeOrderStatus(storeId, orderId);
        return savedOrder.getOrderStatus();
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderStatus setFoodReady(@PathVariable String storeId,
                                    @PathVariable String orderId){
        Order savedOrder = orderService.changeOrderStatus(storeId, orderId);
        return savedOrder.getOrderStatus();
    }

}
