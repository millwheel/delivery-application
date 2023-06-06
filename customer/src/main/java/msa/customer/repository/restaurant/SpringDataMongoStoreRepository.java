package msa.customer.repository.restaurant;

import msa.customer.DAO.FoodKindType;
import msa.customer.DAO.Store;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataMongoStoreRepository extends MongoRepository<Store, String> {
    List<Store> findByLocationNearAndFoodKindIs(Point location, Distance distance, FoodKindType foodKind);
}
