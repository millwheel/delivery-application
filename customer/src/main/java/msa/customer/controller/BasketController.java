package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.dto.basket.BasketResponseDto;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.menu.Menu;
import msa.customer.entity.store.FoodKind;
import msa.customer.service.basket.BasketService;
import msa.customer.service.menu.MenuService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Optional;

@RequestMapping("/customer/{foodKind}/store/{storeId}")
@RestController
public class BasketController {

    private final BasketService basketService;
    private final MenuService menuService;

    public BasketController(BasketService basketService, MenuService menuService) {
        this.basketService = basketService;
        this.menuService = menuService;
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

    @DeleteMapping("/basket/{menuId}")
    public void deleteMenuFromBasket (@RequestAttribute("cognitoUsername") String customerId,
                                      @PathVariable FoodKind foodKind,
                                      @PathVariable String storeId,
                                      @PathVariable String menuId,
                                      HttpServletResponse response) throws IOException {
        if(menuService.getMenu(menuId).isEmpty()){
            throw new RuntimeException("Menu doesn't exist.");
        }
        basketService.deleteMenuFromBasket(customerId, menuId);
        response.sendRedirect("/customer/" + foodKind + "/store/" + storeId + "/basket");
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
