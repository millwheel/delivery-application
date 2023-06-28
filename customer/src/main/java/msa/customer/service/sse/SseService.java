package msa.customer.service.sse;

import lombok.extern.slf4j.Slf4j;
import msa.customer.dto.order.OrderPartResponseDto;
import msa.customer.dto.order.OrderResponseDto;
import msa.customer.entity.order.Order;
import msa.customer.service.order.OrderService;
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
public class SseService {

    private final OrderService orderService;
    private SseEmitter emitterStore;

    @Autowired
    public SseService(OrderService orderService) {
        this.orderService = orderService;
    }

    public SseEmitter connect(String customerId) {
        SseEmitter emitter = new SseEmitter();
        emitterStore = emitter;
        log.info("new emitter created: {}", emitter);
        emitter.onCompletion(() -> {
            emitterStore = null;
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

    public void showOrder(String orderId){
        Optional<Order> orderOptional = orderService.getOrder(orderId);
        if (orderOptional.isEmpty()){
            throw new RuntimeException("order doesn't exist.");
        }
        OrderResponseDto orderResponseDto = new OrderResponseDto(orderOptional.get());
        try {
            emitterStore.send(SseEmitter.event().name("orderList").data(orderResponseDto));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
