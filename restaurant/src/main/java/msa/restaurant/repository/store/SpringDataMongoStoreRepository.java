package msa.restaurant.repository.store;

import msa.restaurant.entity.store.Store;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoStoreRepository extends MongoRepository<Store, String> {
    Optional<List<Store>> findByManagerId(String managerId);
}
