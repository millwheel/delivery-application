package msa.restaurant.repository.menu;

import msa.restaurant.entity.menu.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataMongoMenuRepository extends MongoRepository<Menu, String> {
    List<Menu> findByStoreId(String storeId);
}
