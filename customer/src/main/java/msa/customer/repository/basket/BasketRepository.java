package msa.customer.repository.basket;

import msa.customer.entity.Basket;

import java.util.Optional;

public interface BasketRepository {
    String create(Basket basket);
    Optional<Basket> findById(String basketId);
    void update(String basketId);
    void deleteById(String basketId);
}
