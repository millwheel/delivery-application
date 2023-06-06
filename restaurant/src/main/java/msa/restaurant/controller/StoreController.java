package msa.restaurant.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Store;
import msa.restaurant.DTO.StoreForm;
import msa.restaurant.service.MemberService;
import msa.restaurant.service.StoreJsonService;
import msa.restaurant.service.StoreService;
import msa.restaurant.service.SqsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/restaurant/store")
public class StoreController {

    private final StoreService storeService;
    private final StoreJsonService storeJsonService;
    private final MemberService memberService;
    private final SqsService sqsService;

    public StoreController(StoreService storeService, StoreJsonService storeJsonService, MemberService memberService, SqsService sqsService) {
        this.storeService = storeService;
        this.storeJsonService = storeJsonService;
        this.memberService = memberService;
        this.sqsService = sqsService;
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Store> restaurantList (@RequestAttribute("cognitoUsername") String managerId) {
        return memberService.getRestaurantList(managerId);
    }

    @GetMapping("/enroll")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantAddForm () {
        return "restaurant enroll form";
    }

    @PostMapping("/enroll")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void restaurantAdd (@RequestAttribute("cognitoUsername") String managerId,
                               @RequestBody StoreForm data,
                               HttpServletResponse response) throws IOException {
        String storeId = storeService.createRestaurantInfo(data);
        Store store = storeService.getRestaurant(storeId).get();
        List<Store> storeList = memberService.getRestaurantList(managerId);
        storeList.add(store);
        memberService.updateRestaurantList(managerId, storeList);
        String messageForStoreInfo = storeJsonService.createMessageForStoreInfo(store);
        SendMessageResult sendMessageResult = sqsService.sendToCustomer(messageForStoreInfo);
        log.info("message sending result={}", sendMessageResult);
        response.sendRedirect("/manager/store/list");
    }

    @GetMapping("/update/{restaurantId}")
    @ResponseStatus(HttpStatus.OK)
    public String restaurantUpdateForm(@PathVariable String restaurantId){
        return "restaurant info update form here.";
    }

    @PutMapping("/update/{restaurantId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public void restaurantUpdate(@RequestAttribute("cognitoUsername") String managerId,
                                 @PathVariable String restaurantId,
                                 @RequestBody StoreForm data,
                                 HttpServletResponse response) throws IOException {

        Optional<Store> restaurant = storeService.getRestaurant(restaurantId);
        if (restaurant.isEmpty()){
            response.sendRedirect("/manager/restaurant/update/error");
        }
        storeService.updateRestaurantInfo(restaurantId, data);
        response.sendRedirect("");
    }

    @GetMapping("/update/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String restaurantUpdateError(){
        return "Wrong restaurant id";
    }
}

