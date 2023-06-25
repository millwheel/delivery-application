package msa.customer.repository.basket;

import msa.customer.entity.basket.Basket;
import msa.customer.entity.basket.MenuInBasket;
import org.springframework.stereotype.Repository;


import java.util.Arrays;
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
    public void deleteMenu(String basketId, String menuId) {
        repository.findById(basketId).ifPresent(basket -> {
            List<MenuInBasket> menuInBasketList = basket.getMenuInBasketList();
            Optional<MenuInBasket> menuInBasketOptional = menuInBasketList.stream().filter(m -> m.getMenuId().equals(menuId)).findAny();
            if (menuInBasketOptional.isEmpty()){
                throw new RuntimeException("wrong menu id");
            }
            MenuInBasket menuInBasket = menuInBasketOptional.get();
            menuInBasketList.remove(menuInBasket);
            basket.setMenuInBasketList(menuInBasketList);
            repository.save(basket);
        });
    }

    @Override
    public void deleteBasket(String basketId) {
        repository.deleteById(basketId);
    }
}
