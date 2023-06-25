package msa.customer.service;

import msa.customer.dto.menu.MenuSqsDto;
import msa.customer.entity.menu.Menu;
import msa.customer.entity.store.FoodKindType;
import msa.customer.entity.store.Store;
import msa.customer.dto.store.StoreSqsDto;
import msa.customer.repository.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        storeRepository.createStore(store);
    }

    public Optional<Store> getStore(String storeId){
        return storeRepository.readStore(storeId);
    }

    public void updateStore(StoreSqsDto data){
        storeRepository.updateStore(data);
    }

    public void deleteStore(String storeId){
        storeRepository.deleteStore(storeId);
    }

    public void openStore(String storeId){
        storeRepository.updateOpenStatus(storeId, true);
    }

    public void closeStore(String storeId){
        storeRepository.updateOpenStatus(storeId, false);
    }

    public List<Store> showStoreListsNearCustomer(Point location, FoodKindType foodKind){
        return storeRepository.readStoreNearLocation(location, foodKind);
    }
}
