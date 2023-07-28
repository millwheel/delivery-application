package msa.restaurant.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.pubsub.dto.StoreOrderMatchingMessage;
import msa.restaurant.sse.OrderInfoSseService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.io.IOException;

@Slf4j
public class OrderInfoSubService implements MessageListener {

    private final OrderInfoSseService orderInfoSseService;

    public OrderInfoSubService(OrderInfoSseService orderInfoSseService) {
        this.orderInfoSseService = orderInfoSseService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StoreOrderMatchingMessage storeOrderMatchingMessage = objectMapper.readValue(message.getBody(), StoreOrderMatchingMessage.class);
            String storeId = storeOrderMatchingMessage.getStoreId();
            String orderId = storeOrderMatchingMessage.getOrderId();
            log.info("message customerId={}, orderId={}", storeId, orderId);
            orderInfoSseService.updateOrderInfoFromRedis(storeId, orderId);
        } catch (IOException e) {
            log.error("error occurred={}", e.getMessage());
        }
    }
}
