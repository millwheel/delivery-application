package msa.restaurant.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.store.StorePartResponseDto;
import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.dto.store.StoreResponseDto;
import msa.restaurant.entity.store.Store;
import msa.restaurant.dto.store.StoreSqsDto;
import msa.restaurant.message_queue.SendingMessageConverter;
import msa.restaurant.service.store.StoreService;
import msa.restaurant.message_queue.SqsService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/restaurant/store")
@AllArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StorePartResponseDto> storeList (
            @RequestAttribute("cognitoUsername") String managerId) {
        List<Store> storeList = storeService.getStoreList(managerId);
        List<StorePartResponseDto> storeListDto = new ArrayList<>();
        storeList.forEach(store -> {
            storeListDto.add(new StorePartResponseDto(store));
        });
        return storeListDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createStore (@RequestAttribute("cognitoUsername") String managerId,
                          @Validated @RequestBody StoreRequestDto data) {
        Store store = storeService.createStore(data, managerId);
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToCreateStore = sendingMessageConverter.createMessageToCreateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToCreateStore);
        sqsService.sendToRider(messageToCreateStore);
        return store.getStoreId();
    }

    @GetMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDto storeInfo (@PathVariable String storeId) {
        Store store = storeService.getStore(storeId);
        return new StoreResponseDto(store);
    }

    @PatchMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStore(@PathVariable String storeId,
                            @RequestBody StoreRequestDto data)  {
        Store store = storeService.updateStore(storeId, data);
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToUpdateStore = sendingMessageConverter.createMessageToUpdateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToUpdateStore);
        sqsService.sendToRider(messageToUpdateStore);
    }

    @PostMapping("/{storeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeStoreStatus(@PathVariable String storeId,
                                  @RequestBody boolean open){
        String messageToChangeStatus;
        if (open){
            storeService.openStore(storeId);
            messageToChangeStatus = sendingMessageConverter.createMessageToOpenStore(storeId);
        } else {
            storeService.closeStore(storeId);
            messageToChangeStatus = sendingMessageConverter.createMessageToCloseStore(storeId);
        }
        sqsService.sendToCustomer(messageToChangeStatus);
        sqsService.sendToRider(messageToChangeStatus);
    }

    @DeleteMapping("/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@PathVariable String storeId)  {
        if(!storeService.deleteStore(storeId)) return;
        String messageToDeleteStore = sendingMessageConverter.createMessageToDeleteStore(storeId);
        sqsService.sendToCustomer(messageToDeleteStore);
        sqsService.sendToRider(messageToDeleteStore);
    }
}

