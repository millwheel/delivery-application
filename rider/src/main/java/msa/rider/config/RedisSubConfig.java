package msa.rider.config;

import msa.rider.pubsub.SubService;
import msa.rider.sse.SseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisSubConfig {

    private final SseService sseService;
    private final RedisConfig redisConfig;

    public RedisSubConfig(SseService sseService, RedisConfig redisConfig) {
        this.sseService = sseService;
        this.redisConfig = redisConfig;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new SubService(sseService));
    }

    // To use pub sub
    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConfig.connectToRedisCloud());
        container.addMessageListener(messageListenerAdapter(), new ChannelTopic("rider-matching"));
        return container;
    }
}
