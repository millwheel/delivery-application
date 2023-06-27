package msa.customer.controller;

import jakarta.servlet.http.HttpServletResponse;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/customer/{foodKind}/store")
public class StoreController {
    private final MemberService memberService;
    private final StoreService storeService;

    public StoreController(MemberService memberService, StoreService storeService) {
        this.memberService = memberService;
        this.storeService = storeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StorePartResponseDto> showStoreList(@RequestAttribute("cognitoUsername") String customerId,
                                                    @PathVariable FoodKind foodKind,
                                                    HttpServletResponse response) throws IOException {
        Optional<Point> coordinates = memberService.getCoordinates(customerId);
        if(coordinates.isEmpty()){
            response.sendRedirect("/customer/member/info");
            return null;
        }
        List<StorePartResponseDto> storePartList = new ArrayList<>();
        List<Store> storeList = storeService.showStoreListNearCustomer(coordinates.get(), foodKind);
        storeList.forEach(store -> {
            storePartList.add(new StorePartResponseDto(store));
        });
        return storePartList;
    }

    @GetMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    public StoreResponseDto showStoreInfo (@PathVariable String storeId){
        Optional<Store> store = storeService.getStore(storeId);
        if (store.isEmpty()){
            throw new RuntimeException("store doesn't exist");
        }
        return new StoreResponseDto(store.get());
    }

}
