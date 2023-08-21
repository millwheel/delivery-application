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
/*
    (C) foodKind는 QueryParam으로 받는 것이 더 restful 해보입니다.
    또한 showStoreInfo에서는 foodKind를 사용하지 않기 때문에, 개별 메서드에서 맵핑하는 것이 좋아 보입니다.
*/
@RequestMapping("/customer/{foodKind}/store")
public class StoreController {
    private final MemberService memberService;
    private final StoreService storeService;

    /* (C) lombok의 @RequiredArgsConstructor를 사용하면 좋을 것 같습니다. */
    public StoreController(MemberService memberService, StoreService storeService) {
        this.memberService = memberService;
        this.storeService = storeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StorePartResponseDto> showStoreList(@RequestAttribute("cognitoUsername") String customerId,
                                                    @PathVariable FoodKind foodKind,
                                                    HttpServletResponse response /* 사용하지 않는 param인 것 같습니다. */) throws IOException {
        /*
            1. (A) MemberService에 왜 coordinates를 획득하는 메서드가 있는 것인지 바로 이해하기는 살짝 어려웠습니다.
            coordinates보다는 location이라는 단어가 다른 클래스들에서 사용하는 도메인 용어(Customer.location)와도 통일되고 더 이해하기 좋을 것 같습니다.

            2. (C) Optional 타입은 획득하자마자 필요한 후처리(orElseThrow, isEmpty 등)를 해주어서
            아래의 isEmpty와 같은 optional check 로직이 여러 코드에 퍼지지 않도록 하는 것이 좋다고 생각합니다.
            MemberService.getCoordinates 내에서 orElseThrow 등으로 optional check & un-wrap하고, Point 타입으로 반환해주세요.
        */
        Optional<Point> coordinates = memberService.getCoordinates(customerId);
        if(coordinates.isEmpty()){
            /*
                (R) NPE는 일반적으로 non-nullable한 곳에 null 값이 세팅되었을 때 발생하는 exception입니다.
                Empty coordinates에 NPE를 던지면 의미의 혼동이 있을 것으로 보입니다.
                자바 스펙인 NPE보다는, 프로젝트에서 empty의 의미를 갖는 exception을 custom하는 것이 좋아보입니다.
                empty도 어떤 대상이 empty한지를 세분화하여 나타내면 더 유의미한 예외를 발생시킬 수 있을 것 같습니다.
            */
            throw new NullPointerException("Customer has no location information.");
        }

        /*
            (A) 책 clean code에서는 collection의 자료형을 변수명에 붙이지 말 것을 권장합니다.
            list -> set 등으로 collection 타입이 변경되면 storePartSet으로 변경해야 하는 경우때문에,
            storeParts, stores와 같은 복수형으로 나타내기를 권장합니다.
        */
        List<StorePartResponseDto> storePartList = new ArrayList<>();
        List<Store> storeList = storeService.getStoreListNearCustomer(coordinates.get(), foodKind);
        storeList.forEach(store -> {
            storePartList.add(new StorePartResponseDto(store));
        });
        return storePartList;
    }

    @GetMapping("/{storeId}")
    @ResponseStatus(HttpStatus.OK)
    /*
        (R) Store와 같이 db table에 저장되는 요소들은 최대한 클라이언트 단에서 분리하여 감춰주는 것이 좋은 것 같습니다.
        일반적으로 Store에 대응되는 DTO를 만들어서 사용하는 편입니다.
    */
    public StoreResponseDto showStoreInfo (@RequestAttribute("storeEntity") Store store,
                                           @PathVariable String storeId){
        return new StoreResponseDto(store);
    }

}
