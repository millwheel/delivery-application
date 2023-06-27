package msa.rider.repository.store;

import msa.rider.entity.store.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoStoreRepository extends MongoRepository<Store, String> {
}
