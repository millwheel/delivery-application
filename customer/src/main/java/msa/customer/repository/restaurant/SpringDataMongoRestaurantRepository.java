package msa.customer.repository.restaurant;

import msa.customer.DAO.Restaurant;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataMongoRestaurantRepository extends MongoRepository<Restaurant, String> {
    List<Restaurant> findByLocationNear(Point location, Distance distance);
}
