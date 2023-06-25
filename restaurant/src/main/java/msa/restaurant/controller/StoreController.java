package msa.restaurant.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.store.StorePartInfoResponseDto;
import msa.restaurant.dto.store.StoreRequestDto;
import msa.restaurant.dto.store.StoreResponseDto;
import msa.restaurant.entity.Store;
import msa.restaurant.dto.store.StoreSqsDto;
import msa.restaurant.entity.StorePartInfo;
import msa.restaurant.service.MemberService;
import msa.restaurant.converter.MessageConverter;
import msa.restaurant.service.StoreService;
import msa.restaurant.service.SqsService;
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

    private final MemberService memberService;
    private final StoreService storeService;
    private final MessageConverter messageConverter;
    private final SqsService sqsService;

    public StoreController(StoreService storeService, MessageConverter messageConverter, MemberService memberService, SqsService sqsService) {
        this.storeService = storeService;
        this.messageConverter = messageConverter;
        this.memberService = memberService;
        this.sqsService = sqsService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StorePartInfoResponseDto> storeList (
            @RequestAttribute("cognitoUsername") String managerId) {
        List<StorePartInfoResponseDto> storeResponseDtoList = new ArrayList<>();
        List<StorePartInfo> storePartInfoList = memberService.getStoreList(managerId);
        storePartInfoList.forEach(store -> {
            storeResponseDtoList.add(new StorePartInfoResponseDto(store));
        });
        return storeResponseDtoList;
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
        throw new RuntimeException("Cannot find store info from DB");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addStore (@RequestAttribute("cognitoUsername") String managerId,
                          @RequestBody StoreRequestDto data,
                          HttpServletResponse response) throws IOException {
        String storeId = storeService.createStore(data);
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Cannot add store into DB.");
        }
        Store store = storeOptional.get();
        StorePartInfo storePartInfo = new StorePartInfo();
        storePartInfo.setStoreId(store.getStoreId());
        storePartInfo.setName(store.getName());
        storePartInfo.setAddress(store.getAddress());
        memberService.updateStoreList(managerId, storePartInfo);
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToCreateStore = messageConverter.createMessageToCreateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToCreateStore);
        sqsService.sendToRider(messageToCreateStore);
        response.sendRedirect("/restaurant/store/list");
    }

    @PutMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId,
                            @RequestBody StoreRequestDto data,
                            HttpServletResponse response) throws IOException {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Cannot find store info for update.");
        }
        Store store = storeOptional.get();
        StorePartInfo storePartInfo = new StorePartInfo();
        storePartInfo.setStoreId(store.getStoreId());
        storePartInfo.setName(store.getName());
        storePartInfo.setAddress(store.getAddress());
        storeService.updateStore(storeId, data);
        memberService.updateStoreList(managerId, storePartInfo);
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageToUpdateStore = messageConverter.createMessageToUpdateStore(storeSqsDto);
        sqsService.sendToCustomer(messageToUpdateStore);
        sqsService.sendToRider(messageToUpdateStore);
        response.sendRedirect("/restaurant/store/list");
    }

    @DeleteMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId,
                            HttpServletResponse response) throws IOException {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Cannot Delete Store from DB. It doesn't exist.");
        }
        Store store = storeOptional.get();
        memberService.deleteStoreFromList(managerId, store.getStoreId());
        storeService.deleteStore(storeId);
        String messageToDeleteStore = messageConverter.createMessageToDeleteStore(storeId);
        sqsService.sendToCustomer(messageToDeleteStore);
        sqsService.sendToRider(messageToDeleteStore);
        response.sendRedirect("/restaurant/store/list");
    }
}

