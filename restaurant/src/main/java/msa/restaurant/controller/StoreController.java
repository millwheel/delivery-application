package msa.restaurant.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.StoreRequestDto;
import msa.restaurant.entity.Store;
import msa.restaurant.dto.StoreSqsDto;
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

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Store> storeList (@RequestAttribute("cognitoUsername") String managerId) {
        return memberService.getStoreList(managerId).orElseGet(ArrayList::new);
    }

    @GetMapping("/info/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreSqsDto storeInfo (@RequestAttribute("cognitoUsername") String managerId,
                                  @PathVariable String storeId) {
        return storeService.getStoreInfo(storeId);
    }

    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStore (@RequestAttribute("cognitoUsername") String managerId,
                          @RequestBody StoreRequestDto data,
                          HttpServletResponse response) throws IOException {
        String storeId = storeService.createStoreInfo(data);
        if (storeService.getStore(storeId).isEmpty()){
            throw new RuntimeException("Cannot find store info create just before from DB.");
        }
        Store store = storeService.getStore(storeId).get();
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
        if (storeService.getStore(storeId).isEmpty()){
            throw new RuntimeException("Cannot find store info from DB.");
        }
        storeService.updateStoreInfo(storeId, data);
        Store store = storeService.getStore(storeId).get();
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
        storeService.deleteStore(storeId);
        response.sendRedirect("/restaurant/store/list");
    }
}

