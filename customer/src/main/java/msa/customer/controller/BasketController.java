package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.dto.basket.BasketResponseDto;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.store.FoodKind;
import msa.customer.service.BasketService;
import msa.customer.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RequestMapping("/customer/{foodKind}/store/{storeId}")
@RestController
public class BasketController {

    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @GetMapping("/basket")
    public BasketResponseDto showBasketMenu(@RequestAttribute("cognitoUsername") String customerId){
        Optional<Basket> basketOptional = basketService.getBasket(customerId);
        if (basketOptional.isEmpty()){
            throw new RuntimeException("basket is empty");
        }
        Basket basket = basketOptional.get();
        return new BasketResponseDto(basket);
    }

    @DeleteMapping("/basket")
    public void cleanBasket(@RequestAttribute("cognitoUsername") String customerId,
                            @PathVariable FoodKind foodKind,
                            @PathVariable String storeId,
                            HttpServletResponse response) throws IOException {
        basketService.deleteAllInBasket(customerId);
        response.sendRedirect("/customer/" + foodKind + "/store/" + storeId + "/menu");
    }

}
