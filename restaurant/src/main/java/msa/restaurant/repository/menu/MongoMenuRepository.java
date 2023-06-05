package msa.restaurant.repository.menu;

import msa.restaurant.DAO.Menu;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MongoMenuRepository implements MenuRepository{

    private final SpringDataMongoMenuRepository repository;

    public MongoMenuRepository(SpringDataMongoMenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Menu menu) {
        Menu savedMenu = repository.save(menu);
        return savedMenu.getMenuId();
    }

    @Override
    public Optional<Menu> findById(String menuId) {
        return repository.findById(menuId);
    }

    @Override
    public void updateName(String menuId, String name) {
        repository.findById(menuId).ifPresent(menu -> {
            menu.setName(name);
            repository.save(menu);
        });
    }

    @Override
    public void updatePrice(String menuId, int price) {
        repository.findById(menuId).ifPresent(menu -> {
            menu.setPrice(price);
            repository.save(menu);
        });
    }

    @Override
    public void updateDescription(String menuId, String description) {
        repository.findById(menuId).ifPresent(menu -> {
            menu.setDescription(description);
            repository.save(menu);
        });
    }
}
