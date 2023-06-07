package msa.customer.service;

import msa.customer.DAO.FoodKindType;
import msa.customer.DAO.Menu;
import msa.customer.DAO.Store;
import msa.customer.DTO.StoreForm;
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

    public StoreForm getStoreInfo(String storeId){
        StoreForm storeForm = new StoreForm();
        getName(storeId).ifPresent(storeForm::setName);
        getPhoneNumber(storeId).ifPresent(storeForm::setPhoneNumber);
        getAddress(storeId).ifPresent(storeForm::setAddress);
        getAddressDetail(storeId).ifPresent(storeForm::setAddressDetail);
//        getCoordinates(storeId).ifPresent(storeForm::setLocation);
        getIntroduction(storeId).ifPresent(storeForm::setIntroduction);
        getMenuList(storeId).ifPresent(storeForm::setMenuList);
        return storeForm;
    }

    public void createStoreInfo(StoreForm data){
        Store store = new Store();
        store.setStoreId(data.getStoreId());
        store.setName(data.getName());
        store.setFoodKind(data.getFoodKind());
        store.setPhoneNumber(data.getPhoneNumber());
        store.setAddress(data.getAddress());
        store.setAddressDetail(data.getAddressDetail());
//        store.setLocation(data.getLocation());
        store.setIntroduction(data.getIntroduction());
        store.setMenuList(data.getMenuList());
        storeRepository.create(store);
    }

    public void updateStoreInfo(StoreForm data){
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
