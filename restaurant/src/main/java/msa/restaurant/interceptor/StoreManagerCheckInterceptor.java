package msa.restaurant.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import msa.restaurant.entity.store.Store;
import msa.restaurant.service.store.StoreService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class StoreManagerCheckInterceptor implements HandlerInterceptor {

    private final StoreService storeService;

    public StoreManagerCheckInterceptor(StoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map pathVariables  = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String storeId = (String) pathVariables.get("storeId");
        String managerId = (String) request.getAttribute("cognitoUsername");
        storeService.getStore(managerId, storeId);
        return true;
    }
}
