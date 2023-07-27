package msa.customer.pubsub;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import test.redis.entity.ChatMessage;

@Service
public class PubService {

    private final RedisTemplate<String, Object> redisTemplate;

    public PubService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(ChatMessage chatMessage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String messageString = objectMapper.writeValueAsString(chatMessage);
        redisTemplate.convertAndSend("topic1", messageString);
    }
}
