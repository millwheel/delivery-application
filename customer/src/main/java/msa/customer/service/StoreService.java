package msa.customer.service;

import msa.customer.entity.FoodKindType;
import msa.customer.entity.Menu;
import msa.customer.entity.Store;
import msa.customer.dto.StoreDto;
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

    public Optional<String> getName(String id){
        return storeRepository.findById(id).map(Store::getName);
    }

    public Optional<String> getPhoneNumber(String id){
        return storeRepository.findById(id).map(Store::getPhoneNumber);
    }

    public Optional<String> getAddress(String id){
        return storeRepository.findById(id).map(Store::getAddress);
    }

    public Optional<String> getAddressDetail(String id){
        return storeRepository.findById(id).map(Store::getAddressDetail);
    }

    public Optional<Point> getCoordinates(String id){
        return storeRepository.findById(id).map(Store::getLocation);
    }

    public Optional<String> getIntroduction(String id){
        return storeRepository.findById(id).map(Store::getIntroduction);
    }

    public Optional<List<Menu>> getMenuList(String id){
        return storeRepository.findById(id).map(Store::getMenuList);
    }

    public StoreDto getStoreInfo(String storeId){
        StoreDto storeDto = new StoreDto();
        getName(storeId).ifPresent(storeDto::setName);
        getPhoneNumber(storeId).ifPresent(storeDto::setPhoneNumber);
        getAddress(storeId).ifPresent(storeDto::setAddress);
        getAddressDetail(storeId).ifPresent(storeDto::setAddressDetail);
        getCoordinates(storeId).ifPresent(storeDto::setLocation);
        getIntroduction(storeId).ifPresent(storeDto::setIntroduction);
        getMenuList(storeId).ifPresent(storeDto::setMenuList);
        return storeDto;
    }

    public void createStoreInfo(StoreDto data){
        Store store = new Store();
        store.setStoreId(data.getStoreId());
        store.setName(data.getName());
        store.setFoodKind(data.getFoodKind());
        store.setPhoneNumber(data.getPhoneNumber());
        store.setAddress(data.getAddress());
        store.setAddressDetail(data.getAddressDetail());
        store.setLocation(data.getLocation());
        store.setIntroduction(data.getIntroduction());
        store.setMenuList(data.getMenuList());
        storeRepository.create(store);
    }

    public void updateStoreInfo(StoreDto data){
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
