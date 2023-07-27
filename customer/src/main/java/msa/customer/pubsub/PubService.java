package msa.customer.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.customer.pubsub.dto.CustomerMatchingMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PubService {

    private final RedisTemplate<String, Object> redisTemplate;

    public PubService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessageToMatchCustomer(String customerId, String orderId) {
        CustomerMatchingMessage customerMatchingMessage = new CustomerMatchingMessage(customerId, orderId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageString = objectMapper.writeValueAsString(customerMatchingMessage);
            redisTemplate.convertAndSend("customer-matching", messageString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
