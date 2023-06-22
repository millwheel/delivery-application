package msa.customer.repository.basket;

import msa.customer.entity.basket.Basket;

import java.util.Optional;

public interface BasketRepository {
    String create(Basket basket);
    Optional<Basket> findById(String basketId);
    void update(Basket basket);
    void deleteById(String basketId);
}
