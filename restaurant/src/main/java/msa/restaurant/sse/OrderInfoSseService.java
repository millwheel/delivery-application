package msa.restaurant.sse;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.order.OrderResponseDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.pubsub.PubService;
import msa.restaurant.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class OrderInfoSseService {

    private final OrderService orderService;
    private final PubService pubService;

    private ConcurrentHashMap<String, SseEmitter> emitterList = new ConcurrentHashMap<>();

    @Autowired
    public OrderInfoSseService(OrderService orderService, PubService pubService) {
        this.orderService = orderService;
        this.pubService = pubService;
    }

    public SseEmitter connect(String storeId) {
        SseEmitter emitter = new SseEmitter();
        emitterList.put(storeId, emitter);
        log.info("new emitter added: {}", emitter);
        log.info("emitter list:{}", emitterList);
        emitter.onCompletion(() -> {
            this.emitterList.remove(emitter);
            log.info("emitter deleted: {}", emitter);
        });
        emitter.onTimeout(emitter::complete);
        try{
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return emitter;
    }


    public void showOrderInfo(String storeId, String orderId) {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new NullPointerException("Order doesn't exist.");
        }
        Order order = orderOptional.get();
        if (!storeId.equals(order.getStoreId())){
            throw new IllegalCallerException("This order doesn't belong to the store.");
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto(order);
        try {
            emitterList.get(storeId).send(SseEmitter.event().name("order").data(orderResponseDto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderInfoFromSqs(String storeId, String orderId){
        if (emitterList.contains(storeId)){
            log.info("The server has customerId={}", storeId);
            showOrderInfo(storeId, orderId);
        } else{
            pubService.sendMessageToMatchStore(storeId);
        }
    }

    public void updateOrderInfoFromRedis(String storeId, String orderId){
        if (emitterList.contains(storeId)){
            log.info("The server has customerId={}", storeId);
            showOrderInfo(storeId, orderId);
        }
    }
}
