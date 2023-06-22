package msa.customer.service;

import msa.customer.dto.basket.BasketRequestDto;
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

    public void addToBasket(String basketId, BasketRequestDto basketRequestDto){
        Optional<Basket> basketOptional = basketRepository.findById(basketId);
        Basket basketBefore = basketOptional.orElseGet(Basket::new);
        Basket basketAfter = setUpBasketInfo(basketBefore, basketRequestDto);
        if(basketOptional.isEmpty()){
            basketRepository.create(basketAfter);
        }else{
            basketRepository.update(basketAfter);
        }
    }

    public Basket setUpBasketInfo(Basket basket, BasketRequestDto basketRequestDto){
        String menuId = basketRequestDto.getMenuId();
        int countAdd = basketRequestDto.getCount();
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


}
