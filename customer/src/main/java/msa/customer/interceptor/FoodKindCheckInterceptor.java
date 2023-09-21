package msa.customer.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import msa.customer.entity.store.FoodKind;
import msa.customer.exception.FoodKindNonexistentException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class FoodKindCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariables  = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String foodKindString = (String) pathVariables.get("foodKind");
        try{
            FoodKind.valueOf(foodKindString);
        }catch (Exception e){
            throw new FoodKindNonexistentException(foodKindString);
        }
        return true;
    }
}
