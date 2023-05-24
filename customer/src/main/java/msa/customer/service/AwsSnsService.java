package msa.customer.service;

import com.amazonaws.services.sns.AmazonSNS;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {

    private final AmazonSNS amazonSNS;

    @Value("${aws.sns.topic.arn}")
    private String snsTopicARN;

    public AwsSnsService(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public void publish(String message) {
        amazonSNS.publish(snsTopicARN, message);
    }
}
