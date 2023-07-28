package msa.restaurant.sse;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.order.OrderPartResponseDto;
import msa.restaurant.dto.order.OrderResponseDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.pubsub.PubService;
import msa.restaurant.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class OrderSseService {

    private final OrderService orderService;
    private final PubService pubService;

    private ConcurrentHashMap<String, SseEmitter> emitterList = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> requestList = new ConcurrentHashMap<>();

    @Autowired
    public OrderSseService(OrderService orderService, PubService pubService) {
        this.orderService = orderService;
        this.pubService = pubService;
    }

    public SseEmitter connectForList(String storeId) {
        SseEmitter emitter = new SseEmitter();
        emitterList.put(storeId, emitter);
        requestList.put(storeId, "list");
        log.info("new emitter added: {}", emitter);
        log.info("emitter list:{}", emitterList);
        emitter.onCompletion(() -> {
            SseEmitter removedEmitter = this.emitterList.remove(storeId);
            this.requestList.remove(storeId);
            log.info("emitter deleted: {}", removedEmitter);
        });
        emitter.onTimeout(emitter::complete);
        try{
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return emitter;
    }

    public SseEmitter connectForInfo(String storeId) {
        SseEmitter emitter = new SseEmitter();
        emitterList.put(storeId, emitter);
        requestList.put(storeId, "info");
        log.info("new emitter added: {}", emitter);
        log.info("emitter list:{}", emitterList);
        emitter.onCompletion(() -> {
            SseEmitter removedEmitter = this.emitterList.remove(storeId);
            this.requestList.remove(storeId);
            log.info("emitter deleted: {}", removedEmitter);
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

    public void showOrderInfo(String storeId, String orderId) {
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new NullPointerException("Order doesn't exist.");
        }
        Order order = orderOptional.get();
        if (!storeId.equals(order.getStoreId())){
            throw new IllegalCallerException("This order doesn't belong to the store.");
        }
        OrderResponseDto orderPartInfo = new OrderResponseDto(order);
        try {
            emitterList.get(storeId).send(SseEmitter.event().name("order").data(orderPartInfo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderFromSqs(String storeId, String orderId){
        if (emitterList.contains(storeId)){
            log.info("The server has storeId={}", storeId);
            String request = requestList.get(storeId);
            if (request.equals("list")){
                showOrderList(storeId);
            } else if (request.equals("info")){
                showOrderInfo(storeId, orderId);
            }
        } else{
            pubService.sendMessageToMatchStoreAndOrder(storeId, orderId);
        }
    }

    public void updateOrderFromRedis(String storeId, String orderId){
        if (emitterList.contains(storeId)){
            log.info("The server has storeId={}", storeId);
            String request = requestList.get(storeId);
            if (request.equals("list")){
                showOrderList(storeId);
            } else if (request.equals("info")){
                showOrderInfo(storeId, orderId);
            }
        }
    }

}
