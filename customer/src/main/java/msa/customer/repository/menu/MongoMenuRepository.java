package msa.customer.repository.menu;

import msa.customer.dto.menu.MenuSqsDto;
import msa.customer.entity.menu.Menu;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
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
    public void update(MenuSqsDto data) {
        repository.findById(data.getMenuId()).ifPresent(menu -> {
            menu.setName(data.getName());
            menu.setPrice(data.getPrice());
            menu.setDescription(data.getDescription());
        });
    }

    @Override
    public void deleteById(String menuId) {
        repository.deleteById(menuId);
    }

}
