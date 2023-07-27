package msa.customer.pubsub;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PubService {

    private final RedisTemplate<String, Object> redisTemplate;

    public PubService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessage(String customerId) {
        redisTemplate.convertAndSend("topic1", customerId);
    }
}
