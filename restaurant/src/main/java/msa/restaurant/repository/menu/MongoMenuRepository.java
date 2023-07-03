package msa.restaurant.repository.menu;

import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.entity.menu.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<Menu> readMenu(String menuId) {
        return repository.findById(menuId);
    }

    @Override
    public List<Menu> readMenuList(String storeId) {
        return repository.findByStoreId(storeId);
    }

    @Override
    public void update(String menuId, MenuRequestDto data) {
        repository.findById(menuId).ifPresent(menu -> {
            menu.setName(data.getName());
            menu.setPrice(data.getPrice());
            menu.setDescription(data.getDescription());
            repository.save(menu);
        });
    }

    @Override
    public void deleteById(String menuId) {
        repository.deleteById(menuId);
    }


}
