package msa.customer.repository.menu;

import msa.customer.DAO.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataMongoMenuRepository extends MongoRepository<Menu, String> {
}
