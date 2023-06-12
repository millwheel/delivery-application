package msa.restaurant.repository.menu;

import msa.restaurant.entity.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMenuRepository extends MongoRepository<Menu, String> {
}
