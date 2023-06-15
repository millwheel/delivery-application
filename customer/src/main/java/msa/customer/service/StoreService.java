package msa.customer.service;

import msa.customer.entity.FoodKindType;
import msa.customer.entity.Store;
import msa.customer.dto.StoreSqsDto;
import msa.customer.repository.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }


    public void createStore(StoreSqsDto data){
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

    public void updateStore(StoreSqsDto data){
        storeRepository.update(data);
    }

    public void deleteStore(String storeId){
        storeRepository.deleteById(storeId);
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
