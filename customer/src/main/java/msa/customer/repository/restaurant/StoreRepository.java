package msa.customer.repository.restaurant;

import msa.customer.entity.FoodKindType;
import msa.customer.entity.Menu;
import msa.customer.entity.Store;
import msa.customer.dto.StoreForm;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;

public interface StoreRepository {
    String create(Store store);
    Optional<Store> findById(String id);
    List<Store> findStoreNear(Point location, FoodKindType foodKind);
    List<Store> findAll();
    void update(String storeId, StoreForm data);
    void updateName(String id, String name);
    void updateFoodKind(String id, FoodKindType foodKind);
    void updatePhoneNumber(String id, String phoneNumber);
    void updateAddress(String id, String address);
    void updateAddressDetail(String id, String addressDetail);
    void updateLocation(String id, Point location);
    void updateIntroduction(String id, String introduction);
    void updateMenuList(String id, List<Menu> menuList);
    void updateOpenStatus(String id, boolean open);
    void deleteAll();
}
