package msa.restaurant.service.order;

import lombok.AllArgsConstructor;
import msa.restaurant.dto.rider.RiderPartDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.entity.order.OrderStatus;
import msa.restaurant.exception.OrderStatusUnchangeableException;
import msa.restaurant.message_queue.SendingMessageConverter;
import msa.restaurant.message_queue.SqsService;
import msa.restaurant.repository.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Order getOrder(String storeId, String orderId){
        return orderRepository.readOrder(storeId, orderId);
    }

    public Order changeOrderStatus(String storeId, String orderId){
        Order order = orderRepository.readOrder(storeId, orderId);
        OrderStatus nextStatus = extractNextStatus(order.getOrderStatus());
        Order savedOrder = orderRepository.updateOrderStatus(orderId, nextStatus);
        if (nextStatus == OrderStatus.ORDER_ACCEPT) sendMessageToOrderAccept(savedOrder);
        if (nextStatus == OrderStatus.FOOD_READY) sendMessageToFoodReady(savedOrder);
        return savedOrder;
    }

    private OrderStatus extractNextStatus(OrderStatus prevStatus){
        if (prevStatus == OrderStatus.ORDER_REQUEST){
            return OrderStatus.ORDER_ACCEPT;
        }else if(prevStatus == OrderStatus.RIDER_ASSIGNED){
            return OrderStatus.FOOD_READY;
        }else {
            throw new OrderStatusUnchangeableException(prevStatus);
        }
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
