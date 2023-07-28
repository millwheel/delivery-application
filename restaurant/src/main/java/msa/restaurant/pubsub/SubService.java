package msa.restaurant.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.pubsub.dto.StoreMatchingMessage;
import msa.restaurant.sse.OrderSseService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SubService implements MessageListener {

    private final OrderSseService orderSseService;

    public SubService(OrderSseService orderSseService) {
        this.orderSseService = orderSseService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StoreMatchingMessage storeMatchingMessage = objectMapper.readValue(message.getBody(), StoreMatchingMessage.class);
            String storeId = storeMatchingMessage.getStoreId();
            String orderId = storeMatchingMessage.getOrderId();
            log.info("message customerId={}", storeId);
            orderSseService.updateOrderFromRedis(storeId, orderId);
        } catch (IOException e) {
            log.error("error occurred={}", e.getMessage());
        }
    }
}
