package msa.customer.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import msa.customer.service.store.StoreService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class StoreCheckInterceptor implements HandlerInterceptor {
    private final StoreService storeService;

    public StoreCheckInterceptor(StoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariables  = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String storeId = (String) pathVariables.get("storeId");
        if(storeService.getStore(storeId).isEmpty()){
            throw new NullPointerException("Store doesn't exist.");
        }
        return true;
    }
}
