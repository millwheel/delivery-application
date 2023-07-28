package msa.rider.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.rider.pubsub.dto.RiderMatchingMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PubService {

    private final RedisTemplate<String, Object> redisTemplate;

    public PubService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessageToMatchRider(String riderId, String orderId) {
        RiderMatchingMessage customerMatchingMessage = new RiderMatchingMessage(riderId, orderId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageString = objectMapper.writeValueAsString(customerMatchingMessage);
            redisTemplate.convertAndSend("rider-matching", messageString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
