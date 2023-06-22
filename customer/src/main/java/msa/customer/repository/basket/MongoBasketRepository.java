package msa.customer.repository.basket;

import msa.customer.entity.basket.Basket;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public class MongoBasketRepository implements BasketRepository{

    private final SpringDataMongoBasketRepository repository;

    public MongoBasketRepository(SpringDataMongoBasketRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Basket basket) {
        Basket savedBasket = repository.save(basket);
        return savedBasket.getBasketId();
    }

    @Override
    public Optional<Basket> findById(String basketId) {
        return repository.findById(basketId);
    }

    @Override
    public void update(Basket basket) {
        repository.findById(basket.getBasketId()).ifPresent(savedBasket ->{
            savedBasket.setMenuIdList(basket.getMenuIdList());
            savedBasket.setMenuCountList(basket.getMenuCountList());
            savedBasket.setMenuPriceList(basket.getMenuPriceList());
        });
    }

    @Override
    public void deleteById(String basketId) {
        repository.deleteById(basketId);
    }
}
