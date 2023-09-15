package msa.restaurant.service.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.entity.store.Store;
import msa.restaurant.repository.store.StoreRepository;
import msa.restaurant.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final AddressService addressService;

    public Store getStore(String storeId){
        return storeRepository.readStore(storeId).orElseThrow();
    }

    public List<Store> getStoreList(String managerId){
        return storeRepository.readStoreList(managerId);
    }
    public Store createStore(StoreRequestDto data, String managerId){
        Store store = new Store();
        if(data.getName() == null) throw new NullPointerException("store name is missing");
        store.setName(data.getName());
        if(data.getFoodKind() == null) throw new NullPointerException("store foodKind is missing");
        store.setFoodKind(data.getFoodKind());
        if(data.getPhoneNumber() == null) throw new NullPointerException("store phone number is missing");
        store.setPhoneNumber(data.getPhoneNumber());
        if(data.getAddress() == null) throw new NullPointerException("store address is missing");
        store.setAddress(data.getAddress());
        Point coordinate = addressService.getCoordinate(data.getAddress());
        store.setLocation(coordinate);
        if(data.getAddressDetail() == null) throw new NullPointerException("store address detail is missing");
        store.setAddressDetail(data.getAddressDetail());
        if(data.getIntroduction() == null) throw new NullPointerException("store introduction is missing");
        store.setIntroduction(data.getIntroduction());
        store.setManagerId(managerId);
        store.setOpen(false);
        return storeRepository.create(store);

    }

    public Store updateStore(String storeId, StoreRequestDto data){
        Store store = storeRepository.update(storeId, data);
        Point coordinate = addressService.getCoordinate(data.getAddress());
        storeRepository.updateLocation(storeId, coordinate);
        return store;
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
