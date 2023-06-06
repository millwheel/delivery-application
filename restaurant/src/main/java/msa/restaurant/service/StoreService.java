package msa.restaurant.service;

import msa.restaurant.DAO.FoodKindType;
import msa.restaurant.DAO.Menu;
import msa.restaurant.DAO.Store;
import msa.restaurant.DTO.StoreForm;
import msa.restaurant.repository.store.StoreRepository;
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

    public Optional<Store> getRestaurant(String restaurantId){
        return storeRepository.findById(restaurantId);
    }

    public Optional<String> getName(String restaurantId){
        return storeRepository.findById(restaurantId).map(Store::getName);
    }

    public Optional<String> getPhoneNumber(String restaurantId){
        return storeRepository.findById(restaurantId).map(Store::getPhoneNumber);
    }

    public Optional<String> getAddress(String restaurantId){
        return storeRepository.findById(restaurantId).map(Store::getAddress);
    }

    public Optional<String> getAddressDetail(String restaurantId){
        return storeRepository.findById(restaurantId).map(Store::getAddressDetail);
    }

    public Optional<Point> getCoordinates(String restaurantId){
        return storeRepository.findById(restaurantId).map(Store::getLocation);
    }

    public Optional<String> getIntroduction(String restaurantId){
        return storeRepository.findById(restaurantId).map(Store::getIntroduction);
    }

    public Optional<List<Menu>> getMenuList(String restaurantId){
        return storeRepository.findById(restaurantId).map(Store::getMenuList);
    }

    public StoreForm getRestaurantInfo(String restaurantId){
        StoreForm storeForm = new StoreForm();
        getName(restaurantId).ifPresent(storeForm::setName);
        getPhoneNumber(restaurantId).ifPresent(storeForm::setPhoneNumber);
        getAddress(restaurantId).ifPresent(storeForm::setAddress);
        getAddressDetail(restaurantId).ifPresent(storeForm::setAddressDetail);
        getCoordinates(restaurantId).ifPresent(storeForm::setLocation);
        getIntroduction(restaurantId).ifPresent(storeForm::setIntroduction);
        return storeForm;
    }


    public String createRestaurantInfo(StoreForm data){
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

    public void updateRestaurantInfo(String restaurantId, StoreForm data){
        String name = data.getName();
        FoodKindType foodKind = data.getFoodKind();
        String phoneNumber = data.getPhoneNumber();
        String address = data.getAddress();
        String addressDetail = data.getAddressDetail();
        String introduction = data.getIntroduction();
        if(name != null) storeRepository.updateName(restaurantId, name);
        if(foodKind != null) storeRepository.updateFoodKind(restaurantId, foodKind);
        if(phoneNumber != null) storeRepository.updatePhoneNumber(restaurantId, phoneNumber);
        if(address != null) {
            storeRepository.updateAddress(restaurantId, address);
            Point coordinate = addressService.getCoordinate(address);
            storeRepository.updateLocation(restaurantId, coordinate);
        }
        if(addressDetail != null) storeRepository.updateAddressDetail(restaurantId, addressDetail);
        if(introduction != null) storeRepository.updateIntroduction(restaurantId, introduction);
    }


    public void openRestaurant(String restaurantId){
        storeRepository.updateOpenStatus(restaurantId, true);
    }

    public void closeRestaurant(String restaurantId){
        storeRepository.updateOpenStatus(restaurantId, false);
    }

}
