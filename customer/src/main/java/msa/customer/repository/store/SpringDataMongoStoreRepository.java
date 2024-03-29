package msa.customer.repository.store;

import msa.customer.entity.store.FoodKind;
import msa.customer.entity.store.Store;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataMongoStoreRepository extends MongoRepository<Store, String> {
    List<Store> findByLocationNearAndFoodKindIs(Point location, Distance distance, FoodKind foodKind);
}
