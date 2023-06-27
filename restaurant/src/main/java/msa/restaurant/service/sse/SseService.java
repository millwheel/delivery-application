package msa.restaurant.service.sse;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.dto.order.OrderPartResponseDto;
import msa.restaurant.entity.order.Order;
import msa.restaurant.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SseService {

    private final OrderService orderService;

    private ConcurrentHashMap<String, SseEmitter> emitterList = new ConcurrentHashMap<>();

    @Autowired
    public SseService(OrderService orderService) {
        this.orderService = orderService;
    }

    public SseEmitter connect(String managerId) {
        SseEmitter emitter = new SseEmitter();
        emitterList.put(managerId, emitter);
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
        List<Order> orderList = orderService.getOrderList(storeId).orElseGet(ArrayList::new);
        List<OrderPartResponseDto> orderPartInfoList = new ArrayList<>();
        orderList.forEach(order -> {
            orderPartInfoList.add(new OrderPartResponseDto(order));
        });
        emitterList.forEachValue(Long.MAX_VALUE, emitter -> {
            try {
                emitter.send(SseEmitter.event().name("orderList").data(orderPartInfoList));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
