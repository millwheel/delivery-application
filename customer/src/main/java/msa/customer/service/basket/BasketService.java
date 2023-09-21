package msa.customer.service.basket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.basket.MenuInBasket;
import msa.customer.entity.menu.Menu;
import msa.customer.exception.BasketNonexistentException;
import msa.customer.exception.StoreMismatchException;
import msa.customer.repository.basket.BasketRepository;
import msa.customer.repository.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class BasketService {

    private final BasketRepository basketRepository;
    private final MenuRepository menuRepository;

    public void addToBasket(String basketId, String storeId, String menuId, int count){
        Basket currentBasket = fetchOrInitializeBasket(basketId, storeId);
        Basket updatedBasket = setBasketMenuInfo(currentBasket, menuId, count);
        createOrUpdateBasket(updatedBasket, basketId, storeId);
    }

    private Basket fetchOrInitializeBasket(String basketId, String storeId) {
        return basketRepository.readBasket(basketId)
                .map(b -> {
                    if (!b.getStoreId().equals(storeId)) {
                        throw new StoreMismatchException(storeId);
                    }
                    return b;
                })
                .orElseGet(Basket::new);
    }

    private void createOrUpdateBasket(Basket basket, String basketId, String storeId) {
        if (basket.getBasketId() == null) {
            basket.setBasketId(basketId);
            basket.setStoreId(storeId);
            basketRepository.createBasket(basket);
        } else {
            basketRepository.updateBasket(basket);
        }
    }

    private Basket setBasketMenuInfo(Basket basket, String menuId, int countAdd){
        Menu menu = menuRepository.readMenu(menuId).orElseThrow();
        List<MenuInBasket> menuInBasketList = getMenuInBasketList(basket);
        updateOrCreateMenuInBasket(menu, menuInBasketList, menuId, countAdd);
        updateTotalPrice(basket);
        return basket;
    }

    private List<MenuInBasket> getMenuInBasketList(Basket basket){
        return Optional.ofNullable(basket.getMenuInBasketList()).orElse(new ArrayList<>());
    }

    private void updateOrCreateMenuInBasket(Menu menu, List<MenuInBasket> menuInBasketList, String menuId, int countAdd) {
        menuInBasketList.stream()
                .filter(m -> m.getMenuId().equals(menuId))
                .findAny()
                .ifPresentOrElse(
                        existingMenu -> {
                            existingMenu.setCount(existingMenu.getCount() + countAdd);
                            existingMenu.setPrice(existingMenu.getPrice() + (countAdd * menu.getPrice()));
                        },
                        () -> {
                            MenuInBasket newMenu = MenuInBasket.builder()
                                    .menuId(menuId)
                                    .menuName(menu.getName())
                                    .count(countAdd)
                                    .eachPrice(menu.getPrice())
                                    .price(countAdd * menu.getPrice())
                                    .build();
                            menuInBasketList.add(newMenu);
                        }
                );
    }

    private void updateTotalPrice(Basket basket) {
        int totalPrice = basket.getMenuInBasketList().stream().mapToInt(MenuInBasket::getPrice).sum();
        basket.setTotalPrice(totalPrice);
    }


    public Basket getBasket(String basketId){
        return basketRepository.readBasket(basketId).orElseThrow(() -> new BasketNonexistentException(basketId));
    }

    public void deleteMenuFromBasket(String basketId, String menuId){
        int basketSize = basketRepository.deleteMenu(basketId, menuId);
        if (basketSize == 0){
            basketRepository.deleteBasket(basketId);
        }
    }

    public void deleteAllInBasket(String basketId){
        basketRepository.deleteBasket(basketId);
    }

}
