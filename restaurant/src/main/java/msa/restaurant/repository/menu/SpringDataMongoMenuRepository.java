package msa.restaurant.repository.menu;

import msa.restaurant.DAO.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMenuRepository extends MongoRepository<Menu, String> {
}
