package msa.rider.repository.menu;


import msa.rider.entity.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMenuRepository extends MongoRepository<Menu, String> {
}
