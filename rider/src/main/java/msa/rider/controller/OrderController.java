package msa.rider.controller;

import msa.rider.dto.order.OrderPartResponseDto;
import msa.rider.dto.order.OrderResponseDto;
import msa.rider.dto.rider.RiderPartDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.message_queue.SendingMessageConverter;
import msa.rider.message_queue.SqsService;
import msa.rider.service.order.OrderService;
import msa.rider.sse.SseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/rider/order")
@RestController
public class OrderController {
    private final OrderService orderService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;
    private final SseService sseService;

    @Autowired
    public OrderController(OrderService orderService, SendingMessageConverter sendingMessageConverter, SqsService sqsService, SseService sseService) {
        this.orderService = orderService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.sqsService = sqsService;
        this.sseService = sseService;
    }

    @GetMapping("/new")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderPartResponseDto> showNewOrderList(@RequestAttribute("cognitoUsername") String riderId){
        List<Order> orderListNearRider = orderService.getNewOrderListNearRider(riderId);
        List<OrderPartResponseDto> orderPartResponseDtoList = new ArrayList<>();
        orderListNearRider.forEach(order -> {
            orderPartResponseDtoList.add(new OrderPartResponseDto(order));
        });
        return orderPartResponseDtoList;
    }


    @GetMapping("/new/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderResponseDto showNewOrderInfo(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new NullPointerException("Order doesn't exist. " + orderId + " is not correct order id.");
        }
        return new OrderResponseDto(orderOptional.get());
    }

    @PostMapping("/new/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void riderAssign(@RequestAttribute("cognitoUsername") String riderId,
                                  @PathVariable String orderId) {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new NullPointerException("Order doesn't exist. " + orderId + " is not correct order id.");
        }
        Order order = orderOptional.get();
        RiderPartDto riderPartDto = orderService.updateRiderInfo(riderId, order);
        String messageToAssignRider = sendingMessageConverter.createMessageToAssignRider(order, riderPartDto, OrderStatus.RIDER_ASSIGNED);
        sqsService.sendToRestaurant(messageToAssignRider);
        sqsService.sendToCustomer(messageToAssignRider);
    }

    @GetMapping("/my")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showMyOrderList(@RequestAttribute("cognitoUsername") String riderId){
        SseEmitter sseEmitter = sseService.connectForList(riderId);
        sseService.showOrderList(riderId);
        return sseEmitter;
    }

    @GetMapping("/my/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showOrderInfo(@RequestAttribute("cognitoUsername") String riderId,
                                    @PathVariable String orderId){
        SseEmitter sseEmitter = sseService.connectForInfo(riderId);
        sseService.showOrderInfo(riderId, orderId);
        return sseEmitter;
    }

    @PutMapping("/my/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void changeOrderStatus(@PathVariable String orderId) {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new NullPointerException("Order doesn't exist. " + orderId + " is not correct order id.");
        }
        Order order = orderOptional.get();
        OrderStatus changedOrderStatus = orderService.changeOrderStatusFromClient(orderId, order.getOrderStatus());
        String messageToChangeOrderStatus = sendingMessageConverter.createMessageToChangeOrderStatus(order, changedOrderStatus);
        sqsService.sendToRestaurant(messageToChangeOrderStatus);
        sqsService.sendToCustomer(messageToChangeOrderStatus);
    }
    
}
