package msa.customer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AwsSqsService {
    @SqsListener(value = "Delivery_restaurant_queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void listen(String value) {
        log.info(value);
    }
}