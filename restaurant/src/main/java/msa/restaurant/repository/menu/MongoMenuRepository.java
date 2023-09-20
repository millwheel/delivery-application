package msa.restaurant.repository.menu;

import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.entity.menu.Menu;
import msa.restaurant.exception.MenuNonexistentException;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public Menu readMenu(String menuId) {
        return repository.findById(menuId).orElseThrow(()-> new MenuNonexistentException(menuId));
    }

    @Override
    public List<Menu> readMenuList(String storeId) {
        return repository.findByStoreId(storeId);
    }

    @Override
    public Menu update(String menuId, MenuRequestDto data) {
        Menu menu = repository.findById(menuId).orElseThrow(()-> new MenuNonexistentException(menuId));
        menu.setName(data.getName());
        menu.setPrice(data.getPrice());
        menu.setDescription(data.getDescription());
        return repository.save(menu);
    }

    @Override
    public void delete(String menuId) {
        if(!repository.existsById(menuId)) throw new MenuNonexistentException(menuId);
        repository.deleteById(menuId);
    }

}
