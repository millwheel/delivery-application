package msa.customer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class AwsSqsListener {

    @SqsListener(value = "${aws.sqs.name}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void listen(@Payload String info, @Headers Map<String, String> headers, Acknowledgment ack) {
        log.info("-------------------------------------info {}", info);
        log.info("-------------------------------------headers {}", headers);
        ack.acknowledge();
    }
}
