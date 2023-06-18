package msa.rider.repository.store;

import msa.rider.entity.FoodKindType;
import msa.rider.entity.Store;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataMongoStoreRepository extends MongoRepository<Store, String> {
    List<Store> findByLocationNearAndFoodKindIs(Point location, Distance distance, FoodKindType foodKind);
}
