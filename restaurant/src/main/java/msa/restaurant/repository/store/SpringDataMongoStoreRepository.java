package msa.restaurant.repository.store;

import msa.restaurant.entity.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoStoreRepository extends MongoRepository<Store, String> {
}
