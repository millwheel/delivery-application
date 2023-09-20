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
    public Menu create(Menu menu) {
        return repository.save(menu);
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
    public Menu update(String menuId, MenuRequestDto data) {
        Menu menu = repository.findById(menuId).orElseThrow();
        menu.setName(data.getName());
        menu.setPrice(data.getPrice());
        menu.setDescription(data.getDescription());
        return repository.save(menu);
    }

    @Override
    public boolean delete(String menuId) {
        if(repository.existsById(menuId)){
            repository.deleteById(menuId);
            return true;
        }
        return false;
    }

}
