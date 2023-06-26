package msa.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.entity.store.FoodKind;
import msa.restaurant.entity.store.Store;
import msa.restaurant.repository.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.util.EnumUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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
        return storeRepository.readStore(storeId);
    }

    public Optional<List<Store>> getStoreList(String managerId){
        return storeRepository.readStoreList(managerId);
    }
    public String createStore(StoreRequestDto data, String managerId){
        Store store = new Store();
        if(data.getName().isEmpty()) throw new RuntimeException("store name is missing");
        store.setName(data.getName());
        if(Stream.of(FoodKind.values()).noneMatch(foodKind -> foodKind.equals(data.getFoodKind()))){
            throw new RuntimeException("not valid foodKind");
        }
        store.setFoodKind(data.getFoodKind());
        if(data.getPhoneNumber().isEmpty()) throw new RuntimeException("store phone number is missing");
        store.setPhoneNumber(data.getPhoneNumber());
        if(data.getAddress().isEmpty()) throw new RuntimeException("store address is missing");
        store.setAddress(data.getAddress());
        Point coordinate = addressService.getCoordinate(data.getAddress());
        store.setLocation(coordinate);
        if(data.getAddressDetail().isEmpty()) throw new RuntimeException("store address detail is missing");
        store.setAddressDetail(data.getAddressDetail());
        if(data.getIntroduction().isEmpty()) throw new RuntimeException("store introduction is missing");
        store.setIntroduction(data.getIntroduction());
        store.setManagerId(managerId);
        store.setOpen(false);
        return storeRepository.create(store);
    }

    public void updateStore(String storeId, StoreRequestDto data){
        storeRepository.update(storeId, data);
        Point coordinate = addressService.getCoordinate(data.getAddress());
        storeRepository.updateLocation(storeId, coordinate);
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

}
