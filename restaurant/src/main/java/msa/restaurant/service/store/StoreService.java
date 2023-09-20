package msa.restaurant.service.store;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.store.OpenStatus;
import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.dto.store.StoreResponseDto;
import msa.restaurant.dto.store.StoreSqsDto;
import msa.restaurant.entity.store.Store;
import msa.restaurant.message_queue.SendingMessageConverter;
import msa.restaurant.message_queue.SqsService;
import msa.restaurant.repository.store.StoreRepository;
import msa.restaurant.service.AddressService;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final AddressService addressService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;


    public Store getStore(String storeId){
        return storeRepository.readStore(storeId);
    }

    public List<Store> getStoreList(String managerId){
        return storeRepository.readStoreList(managerId);
    }

    public String createStore(StoreRequestDto data, String managerId) {
        Point coordinate = addressService.getCoordinate(data.getAddress());
        Store store = Store.builder()
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
        Store savedStore = storeRepository.create(store);
        StoreSqsDto storeSqsDto = new StoreSqsDto(savedStore);
        String messageToCreateStore = sendingMessageConverter.createMessageToCreateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToCreateStore);
        sqsService.sendToRider(messageToCreateStore);
        return savedStore.getStoreId();
    }

    public Store updateStore(String storeId, StoreRequestDto data){
        Point location = addressService.getCoordinate(data.getAddress());
        Store store = storeRepository.update(storeId, data, location);
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToUpdateStore = sendingMessageConverter.createMessageToUpdateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToUpdateStore);
        sqsService.sendToRider(messageToUpdateStore);
        return store;
    }

    public boolean deleteStore(String storeId){
        if(!storeRepository.delete(storeId)) return false;
        String messageToDeleteStore = sendingMessageConverter.createMessageToDeleteStore(storeId);
        sqsService.sendToCustomer(messageToDeleteStore);
        sqsService.sendToRider(messageToDeleteStore);
        return true;
    }

    public void changeStoreStatus(String storeId, OpenStatus openStatus){
        if (openStatus == OpenStatus.OPEN){
            openStore(storeId);
        }
        if (openStatus == OpenStatus.CLOSE){
            closeStore(storeId);
        }
    }

    private void openStore(String storeId){
        storeRepository.updateOpenStatus(storeId, true);
        String messageToOpenStore = sendingMessageConverter.createMessageToOpenStore(storeId);
        sqsService.sendToCustomer(messageToOpenStore);
        sqsService.sendToRider(messageToOpenStore);
    }

    private void closeStore(String storeId){
        storeRepository.updateOpenStatus(storeId, false);
        String messageToCloseStore = sendingMessageConverter.createMessageToCloseStore(storeId);
        sqsService.sendToCustomer(messageToCloseStore);
        sqsService.sendToRider(messageToCloseStore);
    }

}
