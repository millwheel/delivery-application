package msa.customer.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import msa.customer.entity.store.Store;
import msa.customer.exception.store.StoreEmptyException;
import msa.customer.service.store.StoreService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

public class StoreCheckInterceptor implements HandlerInterceptor {

    private final StoreService storeService;

    public StoreCheckInterceptor(StoreService storeService) {
        this.storeService = storeService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariables = (Map)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String storeId = (String)pathVariables.get("storeId");
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()) {
            throw new StoreEmptyException(storeId);
        }
        request.setAttribute("storeEntity", storeOptional.get());
        return true;
    }

}
