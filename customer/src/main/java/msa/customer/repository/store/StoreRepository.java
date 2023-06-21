package msa.customer.repository.store;

import msa.customer.entity.store.FoodKindType;
import msa.customer.entity.menu.MenuPartInfo;
import msa.customer.entity.store.Store;
import msa.customer.dto.store.StoreSqsDto;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    String create(Store store);
    Optional<Store> findById(String id);
    List<Store> findStoreNear(Point location, FoodKindType foodKind);
    void update(StoreSqsDto data);
    void updateMenuList(String id, List<MenuPartInfo> menuList);
    void updateOpenStatus(String id, boolean open);
    void deleteById(String id);
}
