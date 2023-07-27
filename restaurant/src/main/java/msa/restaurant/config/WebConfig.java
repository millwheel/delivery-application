package msa.restaurant.config;

import msa.restaurant.interceptor.JoinCheckInterceptor;
import msa.restaurant.interceptor.OrderStoreCheckInterceptor;
import msa.restaurant.interceptor.StoreManagerCheckInterceptor;
import msa.restaurant.service.member.JoinService;
import msa.restaurant.service.member.ParseJwtService;
import msa.restaurant.service.order.OrderService;
import msa.restaurant.service.store.StoreService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ParseJwtService parseJwtService;
    private final JoinService joinService;
    private final StoreService storeService;
    private final OrderService orderService;

    public WebConfig(ParseJwtService parseJwtService, JoinService joinService, StoreService storeService, OrderService orderService) {
        this.parseJwtService = parseJwtService;
        this.joinService = joinService;
        this.storeService = storeService;
        this.orderService = orderService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JoinCheckInterceptor(parseJwtService, joinService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/restaurant", "/restaurant/main", "/error");
        registry.addInterceptor(new StoreManagerCheckInterceptor(storeService))
                .order(2)
                .addPathPatterns("/restaurant/store/**")
                .excludePathPatterns("/restaurant/store");
        registry.addInterceptor(new OrderStoreCheckInterceptor(orderService))
                .order(3)
                .addPathPatterns("/restaurant/store/*/order/**")
                .excludePathPatterns("/restaurant/store/*/order");
    }
}
