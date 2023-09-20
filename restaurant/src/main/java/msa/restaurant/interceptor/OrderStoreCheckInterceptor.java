package msa.restaurant.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import msa.restaurant.entity.order.Order;
import msa.restaurant.exception.OrderMismatchException;
import msa.restaurant.service.order.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
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
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Map pathVariables  = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String storeId = (String) pathVariables.get("storeId");
        String orderId = (String) pathVariables.get("orderId");
        Order order = orderService.getOrder(orderId);
        if (!order.getStoreId().equals(storeId)){
            throw new OrderMismatchException(orderId, storeId);
        }
        request.setAttribute("order", order);
        return true;
    }
}
