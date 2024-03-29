package msa.customer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.store.StorePartResponseDto;
import msa.customer.dto.store.StoreResponseDto;
import msa.customer.entity.store.FoodKind;
import msa.customer.entity.store.Store;
import msa.customer.service.member.MemberService;
import msa.customer.service.store.StoreService;
import org.springframework.data.geo.Point;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/customer/{foodKind}/store")
@AllArgsConstructor
public class StoreController {
    private final MemberService memberService;
    private final StoreService storeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StorePartResponseDto> showStoreList(@RequestAttribute("cognitoUsername") String customerId,
                                                    @PathVariable FoodKind foodKind){
        Point location = memberService.getLocation(customerId);
        List<StorePartResponseDto> storePartList = new ArrayList<>();
        List<Store> storeList = storeService.getStoreListNearCustomer(location, foodKind);
        storeList.forEach(store -> {
            storePartList.add(new StorePartResponseDto(store));
        });
        return storePartList;
    }

    @GetMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDto showStoreInfo (@PathVariable String storeId){
        Store store = storeService.getStore(storeId);
        return new StoreResponseDto(store);
    }

}
