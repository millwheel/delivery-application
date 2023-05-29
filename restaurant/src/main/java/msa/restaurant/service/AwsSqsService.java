package msa.restaurant.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AwsSqsService {

    @Value("${aws.sqs.url}")
    private String sqsUrl;
    @Value("${aws.sqs.name}")
    private String sqsName;

    private final AmazonSQS amazonSQSClient;

    public AwsSqsService(ObjectMapper objectMapper, AmazonSQS amazonSQS, AmazonSQS amazonSQSClient) {
        this.amazonSQSClient = amazonSQSClient;
    }

    public SendMessageResult send(String data){
        SendMessageRequest sendMessageRequest = new SendMessageRequest(sqsUrl, data);
        return amazonSQSClient.sendMessage(sendMessageRequest);
    }

    @Scheduled(fixedDelay = 1000)
    public void receive(){
        try{
            ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(sqsUrl);
            if(!receiveMessageResult.getMessages().isEmpty()){
                Message message = receiveMessageResult.getMessages().get(0);
                log.info("message body={}", message.getBody());
                amazonSQSClient.deleteMessage(sqsUrl, message.getReceiptHandle());
            }
        } catch (QueueDoesNotExistException e){
            log.error("Queue Dose not exist {}", e.getMessage());
        }
    }

}