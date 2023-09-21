package msa.rider.controller;

import lombok.AllArgsConstructor;
import msa.rider.dto.order.OrderPartResponseDto;
import msa.rider.dto.order.OrderResponseDto;
import msa.rider.dto.rider.RiderSqsDto;
import msa.rider.entity.member.Rider;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.message_queue.SendingMessageConverter;
import msa.rider.message_queue.SqsService;
import msa.rider.service.member.MemberService;
import msa.rider.service.order.OrderService;
import msa.rider.sse.ServerSentEvent;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/rider/order")
@RestController
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;
    private final ServerSentEvent sseService;
    private final MemberService memberService;

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
        Order order = orderService.getOrder(orderId);
        return new OrderResponseDto(order);
    }

    @PostMapping("/new/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public void assignRider(@RequestAttribute("cognitoUsername") String riderId,
                                  @PathVariable String orderId) {
        Rider rider = memberService.getRider(riderId);
        RiderSqsDto riderSqsDto = new RiderSqsDto(rider);
        Order order = orderService.assignRider(riderId, riderSqsDto);
        String messageToAssignRider = sendingMessageConverter.createMessageToAssignRider(order, riderSqsDto);
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
    public void changeOrderStatus(@RequestAttribute("cognitoUsername") String riderId,
                                  @PathVariable String orderId) {
        Order order = orderService.changeOrderStatusFromClient(orderId, riderId);
        String messageToChangeOrderStatus = sendingMessageConverter.createMessageToChangeOrderStatus(order, order.getOrderStatus());
        sqsService.sendToRestaurant(messageToChangeOrderStatus);
        sqsService.sendToCustomer(messageToChangeOrderStatus);
    }
    
}
