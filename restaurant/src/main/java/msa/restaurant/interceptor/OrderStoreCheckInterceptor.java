package msa.restaurant.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import msa.restaurant.entity.order.Order;
import msa.restaurant.service.order.OrderService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;
import java.util.Optional;

public class OrderStoreCheckInterceptor implements HandlerInterceptor {

    private final OrderService orderService;

    public OrderStoreCheckInterceptor(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map pathVariables  = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String storeId = (String) pathVariables.get("storeId");
        String orderId = (String) pathVariables.get("orderId");
        Order order = orderService.getOrder(orderId);
        if (!order.getStoreId().equals(storeId)){
            throw new IllegalCallerException("This order doesn't belong to the store");
        }
        request.setAttribute("order", order);
        return true;
    }
}
