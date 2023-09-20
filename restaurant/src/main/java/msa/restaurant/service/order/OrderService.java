package msa.restaurant.service.order;

import lombok.AllArgsConstructor;
import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.message_queue.SendingMessageConverter;
import msa.restaurant.message_queue.SqsService;
import msa.restaurant.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;
    private final OrderStatusUpdatePolicy orderStatusUpdatePolicy;

    public void createOrder(Order order){
        orderRepository.createOrder(order);
    }

    public List<Order> getOrderList(String storeId){
        return orderRepository.readOrderList(storeId);
    }

    public Order getOrder(String orderId){
        return orderRepository.readOrder(orderId);
    }

    public Order changeOrderStatus(Order order){
        OrderStatus nextStatus = orderStatusUpdatePolicy.checkStatusUpdatable(order.getOrderStatus());
        Order savedOrder = orderRepository.updateOrderStatus(order.getOrderId(), nextStatus);
        if (nextStatus == OrderStatus.ORDER_ACCEPT) sendMessageToOrderAccept(savedOrder);
        if (nextStatus == OrderStatus.FOOD_READY) sendMessageToFoodReady(savedOrder);
        return savedOrder;
    }

    private void sendMessageToOrderAccept(Order order){
        String messageToAcceptOrder = sendingMessageConverter.createMessageToAcceptOrder(order);
        String messageToRequestOrder = sendingMessageConverter.createMessageToRequestOrder(order);
        sqsService.sendToCustomer(messageToAcceptOrder);
        sqsService.sendToRider(messageToRequestOrder);
    }

    private void sendMessageToFoodReady(Order order){
        String messageToChangeOrderStatus = sendingMessageConverter.createMessageToChangeOrderStatus(order);
        sqsService.sendToCustomer(messageToChangeOrderStatus);
        sqsService.sendToRider(messageToChangeOrderStatus);
    }

    public void assignRiderToOrder(String orderId, OrderStatus orderStatus, RiderPartDto riderPartDto){
        orderRepository.updateRiderInfo(orderId, orderStatus, riderPartDto);
    }

    public void changeOrderStatusFromOtherServer(String orderId, OrderStatus orderStatus){
        orderRepository.updateOrderStatus(orderId, orderStatus);
    }
}
