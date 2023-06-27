package msa.restaurant.service.messaging;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SqsService {

    @Value("${aws.sqs.url.customer}")
    private String customerSqsUrl;
    @Value("${aws.sqs.url.restaurant}")
    private String restaurantSqsUrl;
    @Value("${aws.sqs.url.rider}")
    private String riderSqsUrl;

    private final AmazonSQS amazonSQSClient;
    private final ReceivingMessageConverter receivingMessageConverter;

    public SqsService(AmazonSQS amazonSQSClient, ReceivingMessageConverter receivingMessageConverter) {
        this.amazonSQSClient = amazonSQSClient;
        this.receivingMessageConverter = receivingMessageConverter;
    }

    public SendMessageResult sendToCustomer(String data){
        SendMessageRequest sendMessageRequest = new SendMessageRequest(customerSqsUrl, data);
        return amazonSQSClient.sendMessage(sendMessageRequest);
    }

    public SendMessageResult sendToRider(String data){
        SendMessageRequest sendMessageRequest = new SendMessageRequest(riderSqsUrl, data);
        return amazonSQSClient.sendMessage(sendMessageRequest);
    }

    @Scheduled(fixedDelay = 1000)
    public void receive() throws JsonProcessingException {
        try{
            ReceiveMessageResult receiveMessageResult = amazonSQSClient.receiveMessage(restaurantSqsUrl);
            if(!receiveMessageResult.getMessages().isEmpty()){
                Message message = receiveMessageResult.getMessages().get(0);
                String messageBody = message.getBody();
                log.info("message body={}", messageBody);
                receivingMessageConverter.processMessage(messageBody);
                amazonSQSClient.deleteMessage(restaurantSqsUrl, message.getReceiptHandle());
            }
        } catch (QueueDoesNotExistException e){
            log.error("Queue Dose not exist {}", e.getMessage());
        }
    }

}