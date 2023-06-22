package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.entity.store.FoodKindType;
import msa.customer.service.BasketService;
import msa.customer.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/customer/{foodKind}/store/{storeId}")
@RestController
public class OrderController {

    private final BasketService basketService;
    private final OrderService orderService;

    public OrderController(BasketService basketService, OrderService orderService) {
        this.basketService = basketService;
        this.orderService = orderService;
    }

    @GetMapping("/basket/clean")
    public void cleanBasket(@RequestAttribute String customerId,
                            @PathVariable FoodKindType foodKind,
                            @PathVariable String storeId,
                            HttpServletResponse response) throws IOException {
        basketService.deleteAllInBasket(customerId);
        response.sendRedirect("/customer/" + foodKind + "/store/" + storeId + "/menu/list");
    }

    @GetMapping
    public void createOrder(@RequestAttribute String customerId){

    }
}
