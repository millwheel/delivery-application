package msa.customer.service;

import msa.customer.entity.basket.Basket;
import msa.customer.entity.menu.Menu;
import msa.customer.repository.basket.BasketRepository;
import msa.customer.repository.menu.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BasketService {

    private final BasketRepository basketRepository;
    private final MenuRepository menuRepository;

    public BasketService(BasketRepository basketRepository, MenuRepository menuRepository) {
        this.basketRepository = basketRepository;
        this.menuRepository = menuRepository;
    }

    public void addToBasket(String basketId, String storeId, String menuId, int count){
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        if (basketOptional.isPresent()){
            if(!basketOptional.get().getStoreId().equals(storeId)){
                throw new RuntimeException("storeId doesn't match existing storeId");
            }
        }
        Basket basketBefore = basketOptional.orElseGet(Basket::new);
        try {
            Basket basketAfter = setUpBasketInfo(basketBefore, menuId, count);
            if(basketOptional.isEmpty()){
                basketRepository.create(basketAfter);
            }else{
                basketRepository.update(basketAfter);
            }
        } catch (Exception e){
            throw e;
        }
    }

    public Basket setUpBasketInfo(Basket basket, String menuId, int countAdd){
        List<String> menuIdList = basket.getMenuIdList();
        List<Integer> menuCountList = basket.getMenuCountList();
        List<Integer> menuPriceList = basket.getMenuPriceList();
        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if (menuOptional.isEmpty()){
            throw new RuntimeException("menu doesn't exist.");
        }
        int eachPrice = menuOptional.get().getPrice();
        if(menuIdList.contains(menuId)){
            int index = menuIdList.indexOf(menuId);
            int thisMenuCount = menuCountList.get(index) + countAdd;
            menuCountList.set(index, thisMenuCount);
            int thisMenuPrice = thisMenuCount * eachPrice;
            menuPriceList.set(index, thisMenuPrice);
        } else {
            menuIdList.add(menuId);
            menuCountList.add(countAdd);
            int thisMenuPrice = eachPrice * countAdd;
            menuPriceList.add(thisMenuPrice);
        }
        basket.setMenuIdList(menuIdList);
        basket.setMenuCountList(menuCountList);
        basket.setMenuPriceList(menuPriceList);
        int totalPrice = menuPriceList.stream().mapToInt(Integer::intValue).sum();
        basket.setTotalPrice(totalPrice);
        return basket;
    }

    public void deleteAllInBasket(String basketId){
        basketRepository.deleteById(basketId);
    }


}
