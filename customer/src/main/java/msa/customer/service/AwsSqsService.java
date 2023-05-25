package msa.customer.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AwsSqsService {

    @Value("${aws.sqs.url}")
    private String sqsUrl;

    private final ObjectMapper objectMapper;
    private final AmazonSQS amazonSQS;

    public AwsSqsService(ObjectMapper objectMapper, AmazonSQS amazonSQS) {
        this.objectMapper = objectMapper;
        this.amazonSQS = amazonSQS;
    }

    public SendMessageResult send(String data){
        SendMessageRequest sendMessageRequest = new SendMessageRequest(sqsUrl, data);
        return amazonSQS.sendMessage(sendMessageRequest);
    }


}