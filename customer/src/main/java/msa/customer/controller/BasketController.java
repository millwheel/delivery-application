package msa.customer.controller;

import msa.customer.dto.basket.BasketResponseDto;
import msa.customer.entity.basket.Basket;
import msa.customer.service.basket.BasketService;
import msa.customer.service.menu.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


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
        // Use customerId as basketId
        String basketId = customerId;
        Basket basket = basketService.getBasket(basketId);
        return new BasketResponseDto(basket);
    }

    @DeleteMapping("/basket/{menuId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMenuFromBasket (@RequestAttribute("cognitoUsername") String customerId,
                                      @PathVariable String menuId) {
        // Use customerId as basketId
        String basketId = customerId;
        basketService.deleteMenuFromBasket(basketId, menuId);
    }

    @DeleteMapping("/basket")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cleanBasket(@RequestAttribute("cognitoUsername") String customerId){
        // Use customerId as basketId
        String basketId = customerId;
        basketService.deleteAllInBasket(basketId);
    }

}
