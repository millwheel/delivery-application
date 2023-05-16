package msa.customer.repository.restaurant;

import msa.customer.DAO.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoRestaurantRepository extends MongoRepository<Restaurant, String> {
}
