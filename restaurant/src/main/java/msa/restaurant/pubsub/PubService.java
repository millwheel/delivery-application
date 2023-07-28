package msa.restaurant.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.restaurant.pubsub.dto.StoreMatchingMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PubService {

    private final RedisTemplate<String, Object> redisTemplate;

    public PubService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessageToMatchStore(String storeId) {
        StoreMatchingMessage storeMatchingMessage = new StoreMatchingMessage();
        storeMatchingMessage.setStoreId(storeId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageString = objectMapper.writeValueAsString(storeMatchingMessage);
            redisTemplate.convertAndSend("store-matching", messageString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessageToMatchStoreAndOrder(String storeId, String orderId){
        StoreMatchingMessage storeMatchingMessage = new StoreMatchingMessage();
        storeMatchingMessage.setStoreId(storeId);
        storeMatchingMessage.setOrderId(orderId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageString = objectMapper.writeValueAsString(storeMatchingMessage);
            redisTemplate.convertAndSend("store-matching", messageString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
