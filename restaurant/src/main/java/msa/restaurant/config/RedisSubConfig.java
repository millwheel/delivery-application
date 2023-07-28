package msa.restaurant.config;

import msa.restaurant.pubsub.SubService;
import msa.restaurant.sse.OrderSseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisSubConfig {

    private final OrderSseService orderSseService;
    private final RedisConfig redisConfig;

    public RedisSubConfig(OrderSseService orderSseService, RedisConfig redisConfig) {
        this.orderSseService = orderSseService;
        this.redisConfig = redisConfig;
    }

    MessageListenerAdapter messageListenerAdapter() {
        return new MessageListenerAdapter(new SubService(orderSseService));
    }

    // To use pub sub
    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConfig.connectToRedisCloud());
        container.addMessageListener(messageListenerAdapter(), new ChannelTopic("store-matching"));
        return container;
    }
}
