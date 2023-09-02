package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
import msa.customer.dto.basket.BasketResponseDto;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.menu.Menu;
import msa.customer.entity.store.FoodKind;
import msa.customer.service.basket.BasketService;
import msa.customer.service.menu.MenuService;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public BasketResponseDto showBasketMenu(@RequestAttribute("cognitoUsername") String customerId){
        Optional<Basket> basketOptional = basketService.getBasket(customerId);
        if (basketOptional.isEmpty()){
            throw new NullPointerException("Basket is empty");
        }
        return new BasketResponseDto(basketOptional.get());
    }

    @DeleteMapping("/basket/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuFromBasket (@RequestAttribute("cognitoUsername") String customerId,
                                      @PathVariable String menuId) {
        Optional<Basket> basketOptional = basketService.getBasket(customerId);
        if (basketOptional.isEmpty()){
            throw new NullPointerException("Basket is empty");
        }
        if(menuService.getMenu(menuId).isEmpty()){
            throw new NullPointerException("Menu doesn't exist.");
        }
        basketService.deleteMenuFromBasket(customerId, menuId);
    }

    @DeleteMapping("/basket")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cleanBasket(@RequestAttribute("cognitoUsername") String customerId){
        Optional<Basket> basketOptional = basketService.getBasket(customerId);
        if (basketOptional.isEmpty()){
            throw new NullPointerException("Basket is empty");
        }
        basketService.deleteAllInBasket(customerId);
    }

}
