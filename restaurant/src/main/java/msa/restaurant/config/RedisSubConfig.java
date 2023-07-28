package msa.restaurant.config;

import msa.restaurant.pubsub.OrderInfoSubService;
import msa.restaurant.pubsub.OrderListSubService;
import msa.restaurant.sse.OrderInfoSseService;
import msa.restaurant.sse.OrderListSseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisSubConfig {

    private final OrderListSseService orderListSseService;
    private final OrderInfoSseService orderInfoSseService;
    private final RedisConfig redisConfig;

    public RedisSubConfig(OrderListSseService orderListSseService, OrderInfoSseService orderInfoSseService, RedisConfig redisConfig) {
        this.orderListSseService = orderListSseService;
        this.orderInfoSseService = orderInfoSseService;
        this.redisConfig = redisConfig;
    }

    MessageListenerAdapter orderListMessageListenerAdapter() {
        return new MessageListenerAdapter(new OrderListSubService(orderListSseService));
    }

    MessageListenerAdapter orderInfoMessageListenerAdapter(){
        return new MessageListenerAdapter(new OrderInfoSubService(orderInfoSseService));
    }

    // To use pub sub
    @Bean
    RedisMessageListenerContainer redisContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConfig.connectToRedisCloud());
        container.addMessageListener(orderListMessageListenerAdapter(), new ChannelTopic("store-matching"));
        container.addMessageListener(orderInfoMessageListenerAdapter(), new ChannelTopic("store-matching"));
        return container;
    }
}
