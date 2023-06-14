package msa.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.StoreRequestDto;
import msa.restaurant.entity.Menu;
import msa.restaurant.entity.Store;
import msa.restaurant.dto.StoreSqsDto;
import msa.restaurant.repository.store.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Optional<List<Menu>> getMenuList(String storeId){
        return storeRepository.findById(storeId).map(Store::getMenuList);
    }

    public Page<Store> getStoreList(String managerId, Pageable pageable){
        return storeRepository.findStoreList(managerId, pageable);
    }

    public StoreSqsDto getStoreInfo(String storeId){
        return storeRepository.findById(storeId).map(StoreSqsDto::new).orElse(null);
    }


    public String createStoreInfo(String managerId, StoreRequestDto data){
        Store store = new Store();
        store.setManagerId(managerId);
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

    public void updateStoreInfo(String storeId, StoreRequestDto data){
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
