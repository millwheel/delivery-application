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

    public Store createStore(StoreRequestDto data, String managerId) {
        validateRequestData(data);

        Point coordinate = addressService.getCoordinate(data.getAddress());

        return Store.builder()
                .name(data.getName())
                .foodKind(data.getFoodKind())
                .phoneNumber(data.getPhoneNumber())
                .address(data.getAddress())
                .addressDetail(data.getAddressDetail())
                .introduction(data.getIntroduction())
                .managerId(managerId)
                .location(coordinate)
                .open(false)
                .build();
    }

    private void validateRequestData(StoreRequestDto data) {
        validateField(data.getName(), "store name is missing");
        validateField(data.getFoodKind(), "store foodKind is missing");
        validateField(data.getPhoneNumber(), "store phone number is missing");
        validateField(data.getAddress(), "store address is missing");
        validateField(data.getAddressDetail(), "store address detail is missing");
        validateField(data.getIntroduction(), "store introduction is missing");
    }

    private void validateField(Object field, String errorMessage) {
        Optional.ofNullable(field).orElseThrow(() -> new NullPointerException(errorMessage));
    }

    public Store updateStore(String storeId, StoreRequestDto data){
        Point location = addressService.getCoordinate(data.getAddress());
        return storeRepository.update(storeId, data, location);
    }

    public boolean deleteStore(String storeId){
        return storeRepository.delete(storeId);
    }

    public void openStore(String storeId){
        storeRepository.updateOpenStatus(storeId, true);
    }

    public void closeStore(String storeId){
        storeRepository.updateOpenStatus(storeId, false);
    }

}
