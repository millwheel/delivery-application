package msa.rider.sse;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import msa.rider.dto.order.OrderPartResponseDto;
import msa.rider.dto.order.OrderResponseDto;
import msa.rider.entity.order.Order;
import msa.rider.exception.OrderMismatchException;
import msa.rider.pubsub.PubService;
import msa.rider.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@AllArgsConstructor
public class ServerSentEvent {

    private final OrderService orderService;
    private final PubService pubService;

    private static ConcurrentHashMap<String, SseEmitter> emitterList = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, String> requestList = new ConcurrentHashMap<>();

    public SseEmitter connect(String riderId){
        SseEmitter emitter = new SseEmitter();
        emitterList.put(riderId, emitter);
        log.info("new emitter added: {}", emitter);
        log.info("emitter list:{}", emitterList);
        emitter.onCompletion(() -> {
            SseEmitter removedEmitter = this.emitterList.remove(riderId);
            this.requestList.remove(riderId);
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

    public SseEmitter connectForList(String riderId) {
        requestList.put(riderId, "list");
        return connect(riderId);
    }

    public SseEmitter connectForInfo(String riderId) {
        requestList.put(riderId, "info");
        return connect(riderId);
    }

    public void showOrderList(String riderId){
        List<Order> orderList = orderService.getOrderListOfRider(riderId);
        List<OrderPartResponseDto> orderPartInfoList = new ArrayList<>();
        orderList.forEach(order -> {
            orderPartInfoList.add(new OrderPartResponseDto(order));
        });
        sendOrderListToSse(riderId, orderPartInfoList);
    }

    private void sendOrderListToSse(String riderId, List<OrderPartResponseDto> orderPartInfoList){
        try {
            SseEmitter sseEmitter = emitterList.get(riderId);
            sseEmitter.send(SseEmitter.event().name("order-list").data(orderPartInfoList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showOrderInfo(String riderId, String orderId){
        Order order = orderService.getOrder(orderId);
        if (!riderId.equals(order.getStoreId())) throw new OrderMismatchException(orderId, riderId);
        OrderResponseDto orderPartInfo = new OrderResponseDto(order);
        sendOrderInfoToSse(riderId, orderPartInfo);
    }

    private void sendOrderInfoToSse(String riderId, OrderResponseDto orderPartInfo){
        try {
            SseEmitter sseEmitter = emitterList.get(riderId);
            sseEmitter.send(SseEmitter.event().name("order").data(orderPartInfo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateOrderFromSqs(String riderId, String orderId){
        if (emitterList.containsKey(riderId)){
            log.info("The server has riderId={}", riderId);
            extractRequest(riderId, orderId);
        } else{
            log.info("The server doesn't have riderId={} redis publish is activated", riderId);
            pubService.sendMessageToMatchRider(riderId, orderId);
        }
    }

    public void updateOrderFromRedis(String riderId, String orderId){
        if (emitterList.containsKey(riderId)){
            log.info("The server has riderId={}", riderId);
            extractRequest(riderId, orderId);
        }
    }

    private void extractRequest(String riderId, String orderId){
        String request = requestList.get(riderId);
        if (request.equals("list")){
            showOrderList(riderId);
        } else if (request.equals("info")){
            showOrderInfo(riderId, orderId);
        }
    }

}
