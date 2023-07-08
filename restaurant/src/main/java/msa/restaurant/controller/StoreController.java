package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.store.StorePartResponseDto;
import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.dto.store.StoreResponseDto;
import msa.restaurant.entity.store.Store;
import msa.restaurant.dto.store.StoreSqsDto;
import msa.restaurant.service.member.MemberService;
import msa.restaurant.service.messaging.SendingMessageConverter;
import msa.restaurant.service.store.StoreService;
import msa.restaurant.service.messaging.SqsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/restaurant/store")
public class StoreController {

    private final StoreService storeService;
    private final SendingMessageConverter sendingMessageConverter;
    private final SqsService sqsService;

    public StoreController(StoreService storeService, SendingMessageConverter sendingMessageConverter, MemberService memberService, SqsService sqsService) {
        this.storeService = storeService;
        this.sendingMessageConverter = sendingMessageConverter;
        this.sqsService = sqsService;
    }

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
    public void createStore (@RequestAttribute("cognitoUsername") String managerId,
                          @RequestBody StoreRequestDto data,
                          HttpServletResponse response) throws IOException {
        String storeId = storeService.createStore(data, managerId);
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Store creation failed.");
        }
        Store store = storeOptional.get();
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToCreateStore = sendingMessageConverter.createMessageToCreateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToCreateStore);
        sqsService.sendToRider(messageToCreateStore);
    }

    @GetMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDto storeInfo (@RequestAttribute("cognitoUsername") String managerId,
                                       @PathVariable String storeId) {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Store doesn't exist.");
        }
        Store store = storeOptional.get();
        if (!store.getManagerId().equals(managerId)){
            throw new RuntimeException("This store doesn't belong to this manager.");
        }
        return new StoreResponseDto(store);
    }

    @PutMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId,
                            @RequestBody StoreRequestDto data,
                            HttpServletResponse response) throws IOException {
        storeService.updateStore(storeId, data);
        Optional<Store> storeOptional = storeService.getStore(storeId);
        Store store = storeOptional.get();
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToUpdateStore = sendingMessageConverter.createMessageToUpdateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToUpdateStore);
        sqsService.sendToRider(messageToUpdateStore);
    }

    @PostMapping("/{storeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void changeStoreStatus(@RequestAttribute("cognitoUsername") String managerId,
                                  @PathVariable String storeId,
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
    public void deleteStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId,
                            HttpServletResponse response) throws IOException {
        storeService.deleteStore(storeId);
        String messageToDeleteStore = sendingMessageConverter.createMessageToDeleteStore(storeId);
        sqsService.sendToCustomer(messageToDeleteStore);
        sqsService.sendToRider(messageToDeleteStore);
    }
}

