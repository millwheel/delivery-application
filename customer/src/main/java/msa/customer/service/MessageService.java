package msa.customer.service;

import msa.customer.entity.order.Order;
import msa.customer.message_queue.SendingMessageConverter;
import msa.customer.message_queue.SqsMessaging;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final SqsMessaging sqsMessaging;
    private final SendingMessageConverter sendingMessageConverter;

    public MessageService(SqsMessaging sqsMessaging, SendingMessageConverter sendingMessageConverter) {
        this.sqsMessaging = sqsMessaging;
        this.sendingMessageConverter = sendingMessageConverter;
    }

    public void SendOrderMessage(Order order){
        String messageToCreateOrder = sendingMessageConverter.createMessageToCreateOrder(order);
        sqsMessaging.sendToRestaurant(messageToCreateOrder);
    }
}
