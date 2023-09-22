package msa.customer.config;

import msa.customer.interceptor.FoodKindCheckInterceptor;
import msa.customer.interceptor.JoinCheckInterceptor;
import msa.customer.service.member.JoinService;
import msa.customer.service.member.ParseJwtService;
import msa.customer.service.store.StoreService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ParseJwtService parseJwtService;
    private final JoinService joinService;
    private final StoreService storeService;

    public WebConfig(ParseJwtService parseJwtService, JoinService joinService, StoreService storeService) {
        this.parseJwtService = parseJwtService;
        this.joinService = joinService;
        this.storeService = storeService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JoinCheckInterceptor(parseJwtService, joinService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/customer", "/customer/main", "/error");
        registry.addInterceptor(new FoodKindCheckInterceptor())
                .order(2)
                .addPathPatterns("/customer/{foodKind}/**")
                .excludePathPatterns("/customer/order/**");
    }
}
