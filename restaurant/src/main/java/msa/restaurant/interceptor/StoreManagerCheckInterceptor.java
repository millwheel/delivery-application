package msa.restaurant.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import msa.restaurant.entity.store.Store;
import msa.restaurant.service.store.StoreService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

public class StoreManagerCheckInterceptor implements HandlerInterceptor {

    private final StoreService storeService;

    public StoreManagerCheckInterceptor(StoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String managerId = (String) request.getAttribute("cognitoUsername");
        Map pathVariables  = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String storeId = (String) pathVariables.get("storeId");
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Store doesn't exist.");
        }
        Store store = storeOptional.get();
        if (!store.getManagerId().equals(managerId)){
            throw new RuntimeException("This store doesn't belong to this manager.");
        }
        return true;
    }
}
