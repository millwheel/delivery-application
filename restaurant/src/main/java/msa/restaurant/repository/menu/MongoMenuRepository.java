package msa.restaurant.repository.menu;

import msa.restaurant.dto.menu.MenuRequestDto;
import msa.restaurant.entity.menu.Menu;
import msa.restaurant.exception.MenuMismatchException;
import msa.restaurant.exception.MenuNonexistentException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

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
    public Menu readMenu(String storeId, String menuId) {
        Menu menu = repository.findById(menuId).orElseThrow(() -> new MenuNonexistentException(menuId));
        if(!Objects.equals(storeId, menu.getStoreId())){
            throw new MenuMismatchException(menuId, storeId);
        }
        return menu;
    }

    @Override
    public List<Menu> readMenuList(String storeId) {
        return repository.findByStoreId(storeId);
    }

    @Override
    public Menu update(String storeId, String menuId, MenuRequestDto data) {
        Menu menu = repository.findById(menuId).orElseThrow(()-> new MenuNonexistentException(menuId));
        if (!Objects.equals(storeId, menu.getStoreId())){
            throw new MenuMismatchException(menuId, storeId);
        }
        menu.setName(data.getName());
        menu.setPrice(data.getPrice());
        menu.setDescription(data.getDescription());
        return repository.save(menu);
    }

    @Override
    public void delete(String storeId, String menuId) {
        Menu menu = repository.findById(menuId).orElseThrow(()-> new MenuNonexistentException(menuId));
        if (!Objects.equals(storeId, menu.getStoreId())){
            throw new MenuMismatchException(menuId, storeId);
        }
        repository.deleteById(menuId);
    }

}
