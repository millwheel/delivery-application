package msa.restaurant.repository.restaurant;

import msa.restaurant.DAO.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoRestaurantRepository extends MongoRepository<Restaurant, String> {
}
