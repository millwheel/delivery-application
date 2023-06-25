package msa.customer.repository.menu;

import msa.customer.entity.menu.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoMenuRepository extends MongoRepository<Menu, String> {
    Optional<List<Menu>> findByStoreId(String storeId);
}
