package msa.customer.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import msa.customer.pubsub.dto.CustomerMatchingMessage;
import msa.customer.sse.SseService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SubService implements MessageListener {

    private final SseService sseService;

    public SubService(SseService sseService) {
        this.sseService = sseService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CustomerMatchingMessage customerMatchingMessage = objectMapper.readValue(message.getBody(), CustomerMatchingMessage.class);
            String customerId = customerMatchingMessage.getCustomerId();
            String orderId = customerMatchingMessage.getOrderId();
            log.info("message customerId={}, orderId={}", customerId, orderId);
            sseService.updateOrderFromRedis(customerId, orderId);
        } catch (IOException e) {
            log.error("error occurred={}", e.getMessage());
        }
    }
}
