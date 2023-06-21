package msa.customer.repository.menu;

import msa.customer.entity.menu.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMenuRepository extends MongoRepository<Menu, String> {
}
