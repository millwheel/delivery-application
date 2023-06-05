package msa.restaurant.repository.restaurant;

import msa.restaurant.DAO.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoRestaurantRepository extends MongoRepository<Store, String> {
}
