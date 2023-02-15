package com.example.delivery.controller.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@Slf4j
public class CustomerOrderController {

    private static final AtomicLong emitterCounter = new AtomicLong();
    private final List<SseEmitter> emitterList = new CopyOnWriteArrayList<>();
    @CrossOrigin
    @GetMapping(value="/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> connect() {
        SseEmitter sseEmitter = new SseEmitter();
        emitterList.add(sseEmitter);
        log.info("new emitter added: {}", sseEmitter);
        log.info("emitter list:{}", emitterList);
        sseEmitter.onTimeout(sseEmitter::complete);
        sseEmitter.onCompletion(()->{
            emitterList.remove(sseEmitter);
            log.info("emitter deleted");
        });
        try {
            sseEmitter.send(SseEmitter.event().name("connect").data("connected!"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(sseEmitter);
    }
    @CrossOrigin
    @PostMapping("/sse")
    public ResponseEntity<Void> order(){
        long count = emitterCounter.incrementAndGet();
        log.info("sseEmitter counter size={}", count);
        emitterList.forEach(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event().name("count").data(count));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return ResponseEntity.ok().build();
    }

}
