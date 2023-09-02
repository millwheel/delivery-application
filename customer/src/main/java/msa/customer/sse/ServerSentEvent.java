package msa.customer.sse;

import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.order.OrderResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.pubsub.PubService;
import msa.customer.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class ServerSentEvent {

    private final OrderService orderService;
    private final PubService pubService;
    ConcurrentHashMap<String, SseEmitter> emitterList = new ConcurrentHashMap<>();

    @Autowired
    public ServerSentEvent(OrderService orderService, PubService pubService) {
        this.orderService = orderService;
        this.pubService = pubService;
    }

    public SseEmitter connect(String customerId) {
        SseEmitter emitter = new SseEmitter();
        emitterList.put(customerId, emitter);
        log.info("new emitter created: {}", emitter);
        emitter.onCompletion(() -> {
            emitterList.remove(emitter);
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

    public void showOrder(String customerId, String orderId){
        Order order = orderService.getOrder(orderId);
        if (!customerId.equals(order.getCustomerId())){
            throw new IllegalCallerException("This order doesn't belong to the customer");
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto(order);
        try {
            emitterList.get(customerId).send(SseEmitter.event().name("order").data(orderResponseDto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderFromSqs(String customerId, String orderId){
        if (emitterList.containsKey(customerId)){
            log.info("The server has customerId={}", customerId);
            showOrder(customerId, orderId);
        } else{
            log.info("The server doesn't have customerId={} redis publish is activated", customerId);
            pubService.sendMessageToMatchCustomer(customerId, orderId);
        }
    }

    public void updateOrderFromRedis(String customerId, String orderId){
        if (emitterList.containsKey(customerId)){
            log.info("The server has customerId={}", customerId);
            showOrder(customerId, orderId);
        }
    }
}
