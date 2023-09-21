package msa.restaurant.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.store.*;
import msa.restaurant.entity.store.Store;
import msa.restaurant.service.store.StoreService;
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
        return storeService.createStore(data, managerId);
    }

    @GetMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDto readStore (@RequestAttribute("cognitoUsername") String managerId,
                                       @PathVariable String storeId) {
        Store store = storeService.getStore(managerId, storeId);
        return new StoreResponseDto(store);
    }

    @PatchMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDto updateStore(@RequestAttribute("cognitoUsername") String managerId,
                                        @PathVariable String storeId,
                                        @RequestBody StoreRequestDto data)  {
        Store store = storeService.updateStore(managerId, storeId, data);
        return new StoreResponseDto(store);

    }

    @PostMapping("/{storeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public StoreResponseDto changeStoreStatus(@RequestAttribute("cognitoUsername") String managerId,
                                  @PathVariable String storeId,
                                  @RequestBody OpenStatus openStatus){
        Store store = storeService.changeStoreStatus(managerId, storeId, openStatus);
        return new StoreResponseDto(store);
    }

    @DeleteMapping("/{storeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStore(@RequestAttribute("cognitoUsername") String managerId,
                            @PathVariable String storeId)  {
        storeService.deleteStore(managerId, storeId);
    }
}

