package msa.rider.config;

import msa.rider.interceptor.JoinCheckInterceptor;
import msa.rider.service.JoinService;
import msa.rider.service.ParseJwtService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final ParseJwtService parseJwtService;
    private final JoinService joinService;

    public WebConfig(ParseJwtService parseJwtService, JoinService joinService) {
        this.parseJwtService = parseJwtService;
        this.joinService = joinService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JoinCheckInterceptor(parseJwtService, joinService))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/rider", "/error");
    }
}
