package msa.rider;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProviderClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CognitoConfig {
    @Value(value="${aws.access-key}")
    private String accesKey;
    @Value(value="${aws.access-secret}")
    private String secretKey;

    @Bean
    public AWSCognitoIdentityProvider CognitoClient(){
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accesKey, secretKey);
        return AWSCognitoIdentityProviderClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).withRegion("us-east-1")
                .build();
    }
}
