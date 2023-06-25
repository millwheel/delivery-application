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
        });
    }

    @Override
    public void deleteBasket(String basketId) {
        repository.deleteById(basketId);
    }
}
