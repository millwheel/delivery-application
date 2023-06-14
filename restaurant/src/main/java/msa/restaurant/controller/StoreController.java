package msa.restaurant.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.StoreRequestDto;
import msa.restaurant.dto.StoreResponseDto;
import msa.restaurant.entity.Store;
import msa.restaurant.dto.StoreSqsDto;
import msa.restaurant.service.MemberService;
import msa.restaurant.converter.MessageConverter;
import msa.restaurant.service.StoreService;
import msa.restaurant.service.SqsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<StoreResponseDto> storeList (
            @RequestAttribute("cognitoUsername") String managerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<StoreResponseDto> storeResponseDtoList = new ArrayList<>();

//        List<Store> storeList = memberService.getStoreList(managerId).orElseGet(ArrayList::new);

        Pageable paging = PageRequest.of(page, size);
        Page<Store> storeListPage = storeService.getStoreList(managerId, paging);
        List<Store> storeList = storeListPage.getContent();

        storeList.forEach(store -> {
            storeResponseDtoList.add(new StoreResponseDto(store));
        });
        return storeResponseDtoList;
    }

    @GetMapping("/info/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDto storeInfo (@RequestAttribute("cognitoUsername") String managerId,
                                  @PathVariable String storeId) {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if(storeOptional.isPresent()){
            Store store = storeOptional.get();
            return new StoreResponseDto(store);
        }
        throw new RuntimeException("Cannot find store by store-id");
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStore (@RequestAttribute("cognitoUsername") String managerId,
                          @RequestBody StoreRequestDto data,
                          HttpServletResponse response) throws IOException {
        String storeId = storeService.createStoreInfo(managerId, data);
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Cannot find store info create just before from DB.");
        }
        Store store = storeOptional.get();
        memberService.updateStoreList(managerId, store);
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageForStoreInfo = messageConverter.createMessageForStoreInfo(storeSqsDto);
        SendMessageResult sendMessageResult = sqsService.sendToCustomer(messageForStoreInfo);
        log.info("message result={}", sendMessageResult);
        response.sendRedirect("/restaurant/store/list");
    }

    @PutMapping("/update/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId,
                            @RequestBody StoreRequestDto data,
                            HttpServletResponse response) throws IOException {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Cannot find store info from DB.");
        }
        Store store = storeOptional.get();
        storeService.updateStoreInfo(storeId, data);
        memberService.updateStoreList(managerId, store);
        StoreSqsDto storeSqsDto = new StoreSqsDto(store);
        String messageForStoreInfo = messageConverter.createMessageForStoreInfo(storeSqsDto);
        SendMessageResult sendMessageResult = sqsService.sendToCustomer(messageForStoreInfo);
        log.info("message sending result={}", sendMessageResult);
        response.sendRedirect("/restaurant/store/list");
    }

    @DeleteMapping("/delete/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId,
                            HttpServletResponse response) throws IOException {
        Optional<Store> storeOptional = storeService.getStore(storeId);
        if (storeOptional.isEmpty()){
            throw new RuntimeException("Cannot Delete Store from DB. It doesn't exist already.");
        }
        Store store = storeOptional.get();
        memberService.deleteStoreFromList(managerId, store.getStoreId());
        storeService.deleteStore(storeId);
        String messageForDeletingStore = messageConverter.createMessageForDeletingStore(storeId);
        SendMessageResult sendMessageResult = sqsService.sendToCustomer(messageForDeletingStore);
        log.info("message sending result={}", sendMessageResult);
        response.sendRedirect("/restaurant/store/list");
    }
}

