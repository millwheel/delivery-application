package msa.customer.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import msa.customer.DTO.EcmDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AwsSqsService {

    @Value("${aws.sqs.queue.url}")
    private String sqsUrl;

    private final ObjectMapper objectMapper;
    private final AmazonSQS amazonSQS;

    public AwsSqsService(ObjectMapper objectMapper, AmazonSQS amazonSQS) {
        this.objectMapper = objectMapper;
        this.amazonSQS = amazonSQS;
    }

    public SendMessageResult sendMessage(EcmDto msg) throws JsonProcessingException {
        SendMessageRequest sendMessageRequest = new SendMessageRequest(sqsUrl,
                objectMapper.writeValueAsString(msg))
                .withMessageGroupId("sqs-test")
                .withMessageDeduplicationId(UUID.randomUUID().toString());
        return amazonSQS.sendMessage(sendMessageRequest);
    }


    @SqsListener(value = "Delivery_restaurant_queue", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
    public void listen(String value) {
        log.info(value);
    }
}