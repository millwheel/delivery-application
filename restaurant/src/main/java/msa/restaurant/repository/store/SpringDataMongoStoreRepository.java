package msa.restaurant.repository.store;

import msa.restaurant.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataMongoStoreRepository extends MongoRepository<Store, String> {
}
