package msa.restaurant.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsService {

    @Value("${aws.sqs.url.customer}")
    private String customerUrl;
    @Value("${aws.sqs.url.restaurant}")
    private String restaurantUrl;
    @Value("${aws.sqs.url.rider}")
    private String riderUrl;

    private final AmazonSQS amazonSQSClient;

    public SqsService(AmazonSQS amazonSQSClient) {
        this.amazonSQSClient = amazonSQSClient;
    }

    public SendMessageResult sendToCustomer(String data){
        SendMessageRequest sendMessageRequest = new SendMessageRequest(customerUrl, data);
        return amazonSQSClient.sendMessage(sendMessageRequest);
    }

    public SendMessageResult sendToRider(String data){
        SendMessageRequest sendMessageRequest = new SendMessageRequest(riderUrl, data);
        return amazonSQSClient.sendMessage(sendMessageRequest);
    }

    @Scheduled(fixedDelay = 1000)
    public void receive(){
        try{
            ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(restaurantUrl);
            if(!receiveMessageResult.getMessages().isEmpty()){
                Message message = receiveMessageResult.getMessages().get(0);
                log.info("message body={}", message.getBody());
                amazonSQSClient.deleteMessage(restaurantUrl, message.getReceiptHandle());
            }
        } catch (QueueDoesNotExistException e){
            log.error("Queue Dose not exist {}", e.getMessage());
        }
    }

}