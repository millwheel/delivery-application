package msa.customer.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import msa.customer.entity.store.FoodKind;
import msa.customer.service.store.StoreService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;

public class FoodKindCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariables  = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String foodKind = (String) pathVariables.get("foodKind");
        if(!Arrays.asList(FoodKind.values()).contains(foodKind)){
            throw new NullPointerException("Wrong food kind.");
        }
        return true;
    }
}
