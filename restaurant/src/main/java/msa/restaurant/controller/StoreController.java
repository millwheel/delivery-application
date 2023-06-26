package msa.restaurant.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.store.StorePartResponseDto;
import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.dto.store.StoreResponseDto;
import msa.restaurant.entity.store.Store;
import msa.restaurant.dto.store.StoreSqsDto;
import msa.restaurant.service.MemberService;
import msa.restaurant.messaging.converter.SendingMessageConverter;
import msa.restaurant.service.StoreService;
import msa.restaurant.messaging.sqs.SqsService;
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
        Optional<List<Store>> storeListOptional = storeService.getStoreList(managerId);
        if (storeListOptional.isEmpty()){
            throw new RuntimeException("No store list.");
        }
        List<StorePartResponseDto> storeListDto = new ArrayList<>();
        List<Store> storeList = storeListOptional.get();
        storeList.forEach(store -> {
            storeListDto.add(new StorePartResponseDto(store));
        });
        return storeListDto;
    }

    @GetMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDto storeInfo (@RequestAttribute("cognitoUsername") String managerId,
                                  @PathVariable String storeId) {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if(storeOptional.isPresent()){
            Store store = storeOptional.get();
            return new StoreResponseDto(store);
        }
        throw new RuntimeException("Can't find store from DB");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addStore (@RequestAttribute("cognitoUsername") String managerId,
                          @RequestBody StoreRequestDto data,
                          HttpServletResponse response) throws IOException {
        String storeId = storeService.createStore(data, managerId);
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Can't find store from DB");
        }
        Store store = storeOptional.get();
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToCreateStore = sendingMessageConverter.createMessageToCreateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToCreateStore);
        sqsService.sendToRider(messageToCreateStore);
        response.sendRedirect("/restaurant/store");
    }

    @PutMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId,
                            @RequestBody StoreRequestDto data,
                            HttpServletResponse response) throws IOException {
        storeService.updateStore(storeId, data);
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Can't find store from DB");
        }
        Store store = storeOptional.get();
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToUpdateStore = sendingMessageConverter.createMessageToUpdateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToUpdateStore);
        sqsService.sendToRider(messageToUpdateStore);
        response.sendRedirect("/restaurant/store");
    }

    @PostMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void changeStoreStatus(@PathVariable String storeId,
                                  @RequestBody boolean open){
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Can't find store from DB");
        }
        String messageToChangeStatus;
        if (open){
            storeService.openStore(storeId);
            messageToChangeStatus = sendingMessageConverter.createMessageToOpenStore(storeId);
        } else {
            storeService.closeStore(storeId);
            messageToChangeStatus = sendingMessageConverter.createMessageToCloseStore(storeId);
        }
        sqsService.sendToCustomer(messageToChangeStatus);
    }

    @DeleteMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId,
                            HttpServletResponse response) throws IOException {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Can't find store from DB");
        }
        storeService.deleteStore(storeId);
        String messageToDeleteStore = sendingMessageConverter.createMessageToDeleteStore(storeId);
        sqsService.sendToCustomer(messageToDeleteStore);
        sqsService.sendToRider(messageToDeleteStore);
        response.sendRedirect("/restaurant/store");
    }
}

