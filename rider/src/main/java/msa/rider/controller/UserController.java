package msa.rider.controller;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import lombok.extern.slf4j.Slf4j;
import msa.rider.DTO.UserSignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path="/api/users")
public class UserController {
    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;

    @Value(value="${aws.cognito.userPoolId}")
    private String userPoolId;
    @Value(value="${aws.cognito.clientId}")
    private String clientId;

    @PostMapping(path="/sign-up")
    public void signUp(@RequestBody UserSignUpRequest userSignUpRequest){
        try{
            AttributeType emailAttribute = new AttributeType().withName("email").withValue(userSignUpRequest.getEmail());
            AttributeType emailVerifiedAttribute = new AttributeType().withName("email_verified").withValue("true");

            AdminCreateUserRequest userRequest = new AdminCreateUserRequest()
                    .withUserPoolId(userPoolId).withUsername(userSignUpRequest.getUsername())
                    .withTemporaryPassword(userSignUpRequest.getPassword())
                    .withUserAttributes(emailAttribute, emailVerifiedAttribute)
                    .withMessageAction(MessageActionType.SUPPRESS)
                    .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL);

            AdminCreateUserResult createUserResult = cognitoClient.adminCreateUser(userRequest);
            log.info("User created. username={}, status={}", createUserResult.getUser().getUsername(),
                    createUserResult.getUser().getUserStatus());

            AdminSetUserPasswordRequest adminSetUserPasswordRequest =
                    new AdminSetUserPasswordRequest().withUsername(userSignUpRequest.getUsername())
                            .withUserPoolId(userPoolId)
                            .withPassword(userSignUpRequest.getPassword()).withPermanent(true);

            cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);

        } catch (AWSCognitoIdentityProviderException e){
            log.error(e.getErrorMessage());
        }
    }
}
