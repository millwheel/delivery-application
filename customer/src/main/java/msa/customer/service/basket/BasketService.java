package msa.customer.service.basket;

import lombok.extern.slf4j.Slf4j;
import msa.customer.entity.basket.Basket;
import msa.customer.entity.basket.MenuInBasket;
import msa.customer.entity.menu.Menu;
import msa.customer.repository.basket.BasketRepository;
import msa.customer.repository.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BasketService {

    private final BasketRepository basketRepository;
    private final MenuRepository menuRepository;

    public BasketService(BasketRepository basketRepository, MenuRepository menuRepository) {
        this.basketRepository = basketRepository;
        this.menuRepository = menuRepository;
    }

    public void addToBasket(String basketId, String storeId, String menuId, int count){
        Optional<Basket> basketOptional = basketRepository.readBasket(basketId);
        if (basketOptional.isPresent()){
            if(!basketOptional.get().getStoreId().equals(storeId)){
                throw new RuntimeException("storeId doesn't match existing storeId");
            }
        }
        Basket basketBefore = basketOptional.orElseGet(Basket::new);
        try {
            Basket basketAfter = setUpBasketMenuInfo(basketBefore, menuId, count);
            if(basketOptional.isEmpty()){
                basketAfter.setBasketId(basketId);
                basketAfter.setStoreId(storeId);
                basketRepository.createBasket(basketAfter);
            }else{
                basketRepository.updateBasket(basketAfter);
            }
        } catch (Exception e){
            throw e;
        }
    }

    public Basket setUpBasketMenuInfo(Basket basket, String menuId, int countAdd){
        Optional<Menu> menuOptional = menuRepository.readMenu(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("menu doesn't exist.");
        }
        Menu menu = menuOptional.get();
        String menuName = menu.getName();
        int eachPrice = menu.getPrice();
        int menuPrice = countAdd * eachPrice;

        List<MenuInBasket> menuInBasketList = basket.getMenuInBasketList();
        if (menuInBasketList == null){
            menuInBasketList = new ArrayList<>();
        }
        Optional<MenuInBasket> menuInBasketOptional = menuInBasketList.stream().filter(m -> m.getMenuId().equals(menuId)).findAny();
        if (menuInBasketOptional.isPresent()){
            MenuInBasket menuInBasket = menuInBasketOptional.get();
            int index = menuInBasketList.indexOf(menuInBasket);
            int countBefore = menuInBasket.getCount();
            menuInBasket.setCount(countBefore + countAdd);
            int priceBefore = menuInBasket.getPrice();
            menuInBasket.setPrice(priceBefore + menuPrice);
            menuInBasketList.set(index, menuInBasket);
        } else {
            MenuInBasket menuInBasket = new MenuInBasket();
            menuInBasket.setMenuId(menuId);
            menuInBasket.setMenuName(menuName);
            menuInBasket.setCount(countAdd);
            menuInBasket.setEachPrice(eachPrice);
            menuInBasket.setPrice(menuPrice);
            menuInBasketList.add(menuInBasket);
        }
        basket.setMenuInBasketList(menuInBasketList);
        int totalPrice = menuInBasketList.stream().mapToInt(MenuInBasket::getPrice).sum();
        basket.setTotalPrice(totalPrice);
        return basket;
    }

    public Optional<Basket> getBasket(String basketId){
        return basketRepository.readBasket(basketId);
    }

    public void deleteMenuFromBasket(String basketId, String menuId){
        basketRepository.deleteMenu(basketId, menuId);
    }

    public void deleteAllInBasket(String basketId){
        basketRepository.deleteBasket(basketId);
    }


}