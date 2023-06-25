package msa.customer.repository.basket;

import msa.customer.entity.basket.Basket;

import java.util.Optional;

public interface BasketRepository {
    String createBasket(Basket basket);
    Optional<Basket> readBasket(String basketId);
    void updateBasket(Basket basket);
    void deleteBasket(String basketId);
}
