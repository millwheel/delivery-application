package msa.customer.service;

import msa.customer.entity.FoodKindType;
import msa.customer.entity.Menu;
import msa.customer.entity.Store;
import msa.customer.dto.StoreSqsDto;
import msa.customer.repository.restaurant.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }


    public void createStoreInfo(StoreSqsDto data){
        Store store = new Store();
        store.setStoreId(data.getStoreId());
        store.setName(data.getName());
        store.setFoodKind(data.getFoodKind());
        store.setPhoneNumber(data.getPhoneNumber());
        store.setAddress(data.getAddress());
        store.setAddressDetail(data.getAddressDetail());
        store.setLocation(data.getLocation());
        store.setIntroduction(data.getIntroduction());
        storeRepository.create(store);
    }

    public void updateStoreInfo(StoreSqsDto data){
        Optional<Store> storeOpt = storeRepository.findById(data.getStoreId());
        if (storeOpt.isEmpty()){
            createStoreInfo(data);
            return;
        }
        storeRepository.update(data.getStoreId(), data);
    }

    public void openStore(String storeId){
        storeRepository.updateOpenStatus(storeId, true);
    }

    public void closeStore(String storeId){
        storeRepository.updateOpenStatus(storeId, false);
    }

    public List<Store> showStoreListsNearCustomer(Point location, FoodKindType foodKind){
        return storeRepository.findStoreNear(location, foodKind);
    }
}
