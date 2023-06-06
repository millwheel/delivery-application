package msa.restaurant.service.store;

import msa.restaurant.DAO.FoodKindType;
import msa.restaurant.DAO.Menu;
import msa.restaurant.DAO.Store;
import msa.restaurant.DTO.StoreForm;
import msa.restaurant.repository.store.StoreRepository;
import msa.restaurant.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final AddressService addressService;

    @Autowired
    public StoreService(StoreRepository storeRepository, AddressService addressService) {
        this.storeRepository = storeRepository;
        this.addressService = addressService;
    }

    public Optional<Store> getStore(String storeId){
        return storeRepository.findById(storeId);
    }

    public Optional<String> getName(String storeId){
        return storeRepository.findById(storeId).map(Store::getName);
    }

    public Optional<String> getPhoneNumber(String storeId){
        return storeRepository.findById(storeId).map(Store::getPhoneNumber);
    }

    public Optional<String> getAddress(String storeId){
        return storeRepository.findById(storeId).map(Store::getAddress);
    }

    public Optional<String> getAddressDetail(String storeId){
        return storeRepository.findById(storeId).map(Store::getAddressDetail);
    }

    public Optional<Point> getCoordinates(String storeId){
        return storeRepository.findById(storeId).map(Store::getLocation);
    }

    public Optional<String> getIntroduction(String storeId){
        return storeRepository.findById(storeId).map(Store::getIntroduction);
    }

    public Optional<List<Menu>> getMenuList(String storeId){
        return storeRepository.findById(storeId).map(Store::getMenuList);
    }

    public StoreForm getStoreInfo(String storeId){
        StoreForm storeForm = new StoreForm();
        getName(storeId).ifPresent(storeForm::setName);
        getPhoneNumber(storeId).ifPresent(storeForm::setPhoneNumber);
        getAddress(storeId).ifPresent(storeForm::setAddress);
        getAddressDetail(storeId).ifPresent(storeForm::setAddressDetail);
        getCoordinates(storeId).ifPresent(storeForm::setLocation);
        getIntroduction(storeId).ifPresent(storeForm::setIntroduction);
        return storeForm;
    }


    public String createStoreInfo(StoreForm data){
        Store store = new Store();
        store.setName(data.getName());
        store.setFoodKind(data.getFoodKind());
        store.setPhoneNumber(data.getPhoneNumber());
        store.setAddress(data.getAddress());
        Point coordinate = addressService.getCoordinate(data.getAddress());
        store.setLocation(coordinate);
        store.setAddressDetail(data.getAddressDetail());
        store.setIntroduction(data.getIntroduction());
        return storeRepository.create(store);
    }

    public void updateStoreInfo(String storeId, StoreForm data){
        String name = data.getName();
        FoodKindType foodKind = data.getFoodKind();
        String phoneNumber = data.getPhoneNumber();
        String address = data.getAddress();
        String addressDetail = data.getAddressDetail();
        String introduction = data.getIntroduction();
        if(name != null) storeRepository.updateName(storeId, name);
        if(foodKind != null) storeRepository.updateFoodKind(storeId, foodKind);
        if(phoneNumber != null) storeRepository.updatePhoneNumber(storeId, phoneNumber);
        if(address != null) {
            storeRepository.updateAddress(storeId, address);
            Point coordinate = addressService.getCoordinate(address);
            storeRepository.updateLocation(storeId, coordinate);
        }
        if(addressDetail != null) storeRepository.updateAddressDetail(storeId, addressDetail);
        if(introduction != null) storeRepository.updateIntroduction(storeId, introduction);
    }


    public void openStore(String storeId){
        storeRepository.updateOpenStatus(storeId, true);
    }

    public void closeStore(String storeId){
        storeRepository.updateOpenStatus(storeId, false);
    }

}
