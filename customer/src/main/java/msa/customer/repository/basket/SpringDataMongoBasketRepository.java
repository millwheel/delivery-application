package msa.customer.repository.basket;

import msa.customer.entity.Basket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoBasketRepository extends MongoRepository<Basket, String> {
}
