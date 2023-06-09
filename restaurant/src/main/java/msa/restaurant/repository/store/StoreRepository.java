package msa.restaurant.repository.store;

import msa.restaurant.DAO.FoodKindType;
import msa.restaurant.DAO.Menu;
import msa.restaurant.DAO.Store;
import msa.restaurant.DTO.StoreForm;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    String create(Store store);
    Optional<Store> findById(String storeId);
    List<Store> findAll();
    void update(String storeId, StoreForm data);
    void updateLocation(String storeId, Point location);
    void updateMenuList(String storeId, List<Menu> menuList);
    void updateOpenStatus(String storeId, boolean open);
    void deleteAll();
}
