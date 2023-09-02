package msa.customer.repository.basket;

import msa.customer.entity.basket.Basket;
import msa.customer.entity.basket.MenuInBasket;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class MongoBasketRepository implements BasketRepository{

    private final SpringDataMongoBasketRepository repository;

    public MongoBasketRepository(SpringDataMongoBasketRepository repository) {
        this.repository = repository;
    }

    @Override
    public String createBasket(Basket basket) {
        Basket savedBasket = repository.save(basket);
        return savedBasket.getBasketId();
    }

    @Override
    public Optional<Basket> readBasket(String basketId) {
        return repository.findById(basketId);
    }

    @Override
    public void updateBasket(Basket basket) {
        repository.findById(basket.getBasketId()).ifPresent(savedBasket ->{
            savedBasket.setMenuInBasketList(basket.getMenuInBasketList());
            savedBasket.setTotalPrice(basket.getTotalPrice());
            repository.save(basket);
        });
    }

    @Override
    public int deleteMenu(String basketId, String menuId) {
        Basket basket = repository.findById(basketId).orElseThrow();
        List<MenuInBasket> menusInBasket = basket.getMenuInBasketList();
        MenuInBasket menuInBasket = menusInBasket.stream().filter(element -> element.getMenuId().equals(menuId)).findAny().orElseThrow();
        menusInBasket.remove(menuInBasket);
        basket.setMenuInBasketList(menusInBasket);
        repository.save(basket);
        return menusInBasket.size();
    }

    @Override
    public void deleteBasket(String basketId) {
        repository.deleteById(basketId);
    }
}
