package msa.rider.controller;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.servlet.http.HttpServletResponse;
import msa.rider.dto.order.OrderPartResponseDto;
import msa.rider.dto.order.OrderResponseDto;
import msa.rider.dto.rider.RiderPartDto;
import msa.rider.entity.order.Order;
import msa.rider.entity.order.OrderStatus;
import msa.rider.service.member.MemberService;
import msa.rider.service.messaging.SendingMessageConverter;
import msa.rider.service.messaging.SqsService;
import msa.rider.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/rider/order")
@RestController
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final SqsService sseService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

    @Autowired
    public OrderController(OrderService orderService, MemberService memberService, SqsService sseService, SendingMessageConverter sendingMessageConverter, SqsService sqsService) {
        this.orderService = orderService;
        this.memberService = memberService;
        this.sseService = sseService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.sqsService = sqsService;
    }

    @GetMapping("/new")
    public List<OrderPartResponseDto> showNewOrderList(@RequestAttribute("cognitoUsername") String riderId){
        List<Order> orderListNearRider = orderService.getOrderListNearRider(riderId);
        List<OrderPartResponseDto> orderPartResponseDtoList = new ArrayList<>();
        orderListNearRider.forEach(order -> {
            orderPartResponseDtoList.add(new OrderPartResponseDto(order));
        });
        return orderPartResponseDtoList;
    }


    @GetMapping("/new/{orderId}")
    public OrderResponseDto showNewOrderInfo(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        return new OrderResponseDto(orderOptional.get());
    }

    @PostMapping("/new/{orderId}")
    public void riderAssign(@RequestAttribute("cognitoUsername") String riderId,
                                  @PathVariable String orderId,
                                  HttpServletResponse response) throws IOException {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        Order order = orderOptional.get();
        OrderStatus orderStatus = order.getOrderStatus();
        if (!orderStatus.equals(OrderStatus.RIDER_ASSIGNED)){
            throw new RuntimeException("invalid order status");
        }
        OrderStatus changedOrderStatus = orderService.changeOrderStatusFromClient(orderId, orderStatus);
        RiderPartDto riderPartDto = orderService.updateRiderInfo(orderId, riderId);
        String messageToAssignRider = sendingMessageConverter.createMessageToAssignRider(orderId, riderPartDto, changedOrderStatus);
        sqsService.sendToRestaurant(messageToAssignRider);
        sqsService.sendToCustomer(messageToAssignRider);
        response.sendRedirect("/restaurant/order/new");
    }

    @GetMapping("/my")
    public List<OrderPartResponseDto> showMyOrderList(@RequestAttribute("cognitoUsername") String riderId){
        List<Order> orderListOfRider = orderService.getOrderListOfRider(riderId);
        List<OrderPartResponseDto> orderPartResponseDtoList = new ArrayList<>();
        orderListOfRider.forEach(order -> {
            orderPartResponseDtoList.add(new OrderPartResponseDto(order));
        });
        return orderPartResponseDtoList;
    }

    @GetMapping("/my/{orderId}")
    public OrderResponseDto showOrderInfo(@PathVariable String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        return new OrderResponseDto(orderOptional.get());
    }

    @PostMapping("/my/{orderId}")
    public void changeOrderStatus(@PathVariable String orderId,
                                  HttpServletResponse response) throws IOException {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        Order order = orderOptional.get();
        OrderStatus orderStatus = order.getOrderStatus();
        OrderStatus changedOrderStatus = orderService.changeOrderStatusFromClient(orderId, orderStatus);
        String messageToChangeOrderStatus = sendingMessageConverter.createMessageToChangeOrderStatus(orderId, changedOrderStatus);
        sqsService.sendToRestaurant(messageToChangeOrderStatus);
        sqsService.sendToCustomer(messageToChangeOrderStatus);
        response.sendRedirect("/restaurant/order/my" + orderId);
    }


}
