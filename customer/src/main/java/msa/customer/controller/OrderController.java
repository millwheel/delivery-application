package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.dto.basket.BasketResponseDto;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.order.Order;
import msa.customer.entity.store.FoodKindType;
import msa.customer.service.BasketService;
import msa.customer.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RequestMapping("/customer/{foodKind}/store/{storeId}")
@RestController
public class OrderController {

    private final BasketService basketService;
    private final OrderService orderService;

    public OrderController(BasketService basketService, OrderService orderService) {
        this.basketService = basketService;
        this.orderService = orderService;
    }

    @GetMapping("/basket/info")
    public BasketResponseDto showBasketMenu(@RequestAttribute String customerId){
        Optional<Basket> basketOptional = basketService.getBasket(customerId);
        if (basketOptional.isEmpty()){
            throw new RuntimeException("basket is empty");
        }
        Basket basket = basketOptional.get();
        return new BasketResponseDto(basket);
    }

    @GetMapping("/basket/clean")
    public void cleanBasket(@RequestAttribute String customerId,
                            @PathVariable FoodKindType foodKind,
                            @PathVariable String storeId,
                            HttpServletResponse response) throws IOException {
        basketService.deleteAllInBasket(customerId);
        response.sendRedirect("/customer/" + foodKind + "/store/" + storeId + "/menu/list");
    }

    @PostMapping("/order")
    public void createOrder(@RequestAttribute String customerId,
                            @PathVariable String storeId){
        Order order = orderService.createOrder(customerId, storeId, customerId);
    }


}
