package msa.restaurant.sse;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.order.OrderPartResponseDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.pubsub.PubService;
import msa.restaurant.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class OrderListSseService {

    private final OrderService orderService;
    private final PubService pubService;

    private ConcurrentHashMap<String, SseEmitter> emitterList = new ConcurrentHashMap<>();

    @Autowired
    public OrderListSseService(OrderService orderService, PubService pubService) {
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

    public void showOrderList(String storeId) {
        List<Order> orderList = orderService.getOrderList(storeId);
        List<OrderPartResponseDto> orderPartInfoList = new ArrayList<>();
        orderList.forEach(order -> {
            orderPartInfoList.add(new OrderPartResponseDto(order));
        });
        try {
            emitterList.get(storeId).send(SseEmitter.event().name("order-list").data(orderPartInfoList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderListFromSqs(String storeId){
        if (emitterList.contains(storeId)){
            showOrderList(storeId);
        } else{
            pubService.sendMessageToMatchStore(storeId);
        }
    }

    public void updateOrderFromRedis(String storeId){
        if (emitterList.contains(storeId)){
            log.info("The server has customerId={}", storeId);
            showOrderList( storeId);
        }
    }
}
