package msa.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.entity.FoodKindType;
import msa.restaurant.entity.Menu;
import msa.restaurant.entity.Store;
import msa.restaurant.dto.StoreForm;
import msa.restaurant.repository.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    public FoodKindType extractFoodKind(String foodKind){
        return switch (foodKind) {
            case "피자" -> FoodKindType.PIZZA;
            case "치킨" -> FoodKindType.CHICKEN;
            case "일식" -> FoodKindType.JAPANESE;
            case "중식" -> FoodKindType.CHINESE;
            case "한식" -> FoodKindType.KOREAN;
            case "동남아" -> FoodKindType.SOUTH_EAST;
            case "양식" -> FoodKindType.WEST;
            case "디저트" -> FoodKindType.DESSERT;
            default -> null;
        };
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
        log.info("Food kind is={}", store.getFoodKind());
        return storeRepository.create(store);
    }

    public void updateStoreInfo(String storeId, StoreForm data){
        storeRepository.update(storeId, data);
        Point coordinate = addressService.getCoordinate(data.getAddress());
        storeRepository.updateLocation(storeId, coordinate);
    }


    public void openStore(String storeId){
        storeRepository.updateOpenStatus(storeId, true);
    }

    public void closeStore(String storeId){
        storeRepository.updateOpenStatus(storeId, false);
    }

}
