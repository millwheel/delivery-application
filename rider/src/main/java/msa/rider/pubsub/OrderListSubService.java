package msa.rider.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import msa.rider.pubsub.dto.RiderMatchingMessage;
import msa.rider.sse.SseService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class OrderListSubService implements MessageListener {

    private final SseService sseService;

    public OrderListSubService(SseService sseService) {
        this.sseService = sseService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RiderMatchingMessage customerMatchingMessage = objectMapper.readValue(message.getBody(), RiderMatchingMessage.class);
            String riderId = customerMatchingMessage.getRiderId();
            String orderId = customerMatchingMessage.getOrderId();
            log.info("message customerId={}", riderId);
            log.info("message orderId={}", orderId);
            sseService.updateOrderFromRedis(riderId, orderId);
        } catch (IOException e) {
            log.error("error occurred={}", e.getMessage());
        }
    }
}
