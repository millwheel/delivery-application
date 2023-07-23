package msa.customer.sse;

import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.order.OrderResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseService {

    private final OrderService orderService;
    ConcurrentHashMap<String, SseEmitter> emitterList = new ConcurrentHashMap<>();

    @Autowired
    public SseService(OrderService orderService) {
        this.orderService = orderService;
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
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new NullPointerException("order doesn't exist.");
        }
        Order order = orderOptional.get();
        if (!customerId.equals(order.getCustomerId())){
            throw new RuntimeException("This order doesn't belong to the customer");
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto(order);
        try {
            emitterList.get(customerId).send(SseEmitter.event().name("order").data(orderResponseDto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
