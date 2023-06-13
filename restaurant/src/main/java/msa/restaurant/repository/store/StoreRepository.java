package msa.restaurant.repository.store;

import msa.restaurant.dto.StoreRequestDto;
import msa.restaurant.entity.Menu;
import msa.restaurant.entity.Store;
import msa.restaurant.dto.StoreSqsDto;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    String create(Store store);
    Optional<Store> findById(String storeId);
    List<Store> findAll();
    void update(String storeId, StoreRequestDto data);
    void updateLocation(String storeId, Point location);
    void updateMenuList(String storeId, List<Menu> menuList);
    void updateOpenStatus(String storeId, boolean open);
    void deleteById(String storeId);
}
