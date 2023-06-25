package msa.customer.repository.store;

import msa.customer.entity.menu.Menu;
import msa.customer.entity.store.FoodKindType;
import msa.customer.entity.store.Store;
import msa.customer.dto.store.StoreSqsDto;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    String createStore(Store store);
    Optional<Store> readStore(String id);
    List<Store> readStoreNearLocation(Point location, FoodKindType foodKind);
    void updateStore(StoreSqsDto data);
    void updateMenuList(String id, List<Menu> menuList);
    void updateOpenStatus(String id, boolean open);
    void deleteStore(String id);
}
