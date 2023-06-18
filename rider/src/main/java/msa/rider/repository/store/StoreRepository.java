package msa.rider.repository.store;

import msa.rider.dto.StoreSqsDto;
import msa.rider.entity.FoodKindType;
import msa.rider.entity.MenuPartInfo;
import msa.rider.entity.Store;
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
