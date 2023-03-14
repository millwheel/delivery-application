package msa.rider.controller;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import lombok.extern.slf4j.Slf4j;
import msa.rider.DTO.UserDetail;
import msa.rider.DTO.UserSignInRequest;
import msa.rider.DTO.UserSignInResponse;
import msa.rider.DTO.UserSignUpRequest;
import msa.rider.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping(path = "/sign-in")
    public @ResponseBody UserSignInResponse signIn(@RequestBody UserSignInRequest userSignInRequest) {

        UserSignInResponse userSignInResponse = new UserSignInResponse();

        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", userSignInRequest.getUsername());
        authParams.put("PASSWORD", userSignInRequest.getPassword());

        final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
        authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH).withClientId(clientId)
                .withUserPoolId(userPoolId).withAuthParameters(authParams);

        try {
            AdminInitiateAuthResult result = cognitoClient.adminInitiateAuth(authRequest);

            AuthenticationResultType authenticationResult = null;

            if (result.getChallengeName() != null && !result.getChallengeName().isEmpty()) {

                log.info("Challenge name={}", result.getChallengeName());

                if (result.getChallengeName().contentEquals("NEW_PASSWORD_REQUIRED")) {
                    if (userSignInRequest.getPassword() == null) {
                        throw new CustomException(
                                "User must change password " + result.getChallengeName());

                    } else {

                        final Map<String, String> challengeResponses = new HashMap<>();
                        challengeResponses.put("USERNAME", userSignInRequest.getUsername());
                        challengeResponses.put("PASSWORD", userSignInRequest.getPassword());
                        // add new password
                        challengeResponses.put("NEW_PASSWORD", userSignInRequest.getNewPassword());

                        final AdminRespondToAuthChallengeRequest request =
                                new AdminRespondToAuthChallengeRequest()
                                        .withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
                                        .withChallengeResponses(challengeResponses)
                                        .withClientId(clientId).withUserPoolId(userPoolId)
                                        .withSession(result.getSession());

                        AdminRespondToAuthChallengeResult resultChallenge =
                                cognitoClient.adminRespondToAuthChallenge(request);
                        authenticationResult = resultChallenge.getAuthenticationResult();

                        userSignInResponse.setAccessToken(authenticationResult.getAccessToken());
                        userSignInResponse.setIdToken(authenticationResult.getIdToken());
                        userSignInResponse.setRefreshToken(authenticationResult.getRefreshToken());
                        userSignInResponse.setExpiresIn(authenticationResult.getExpiresIn());
                        userSignInResponse.setTokenType(authenticationResult.getTokenType());
                    }

                } else {
                    throw new CustomException(
                            "User has other challenge " + result.getChallengeName());
                }
            } else {
                log.info("User has no chanllenge");
                authenticationResult = result.getAuthenticationResult();

                userSignInResponse.setAccessToken(authenticationResult.getAccessToken());
                userSignInResponse.setIdToken(authenticationResult.getIdToken());
                userSignInResponse.setRefreshToken(authenticationResult.getRefreshToken());
                userSignInResponse.setExpiresIn(authenticationResult.getExpiresIn());
                userSignInResponse.setTokenType(authenticationResult.getTokenType());
            }

        } catch (InvalidParameterException e) {
            throw new CustomException(e.getErrorMessage());
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
        cognitoClient.shutdown();
        return userSignInResponse;
    }

    @GetMapping(path = "/detail")
    public @ResponseBody UserDetail getUserDetail() {

        UserDetail userDetail = new UserDetail();
        userDetail.setFirstName("Test");
        userDetail.setLastName("Buddy");
        userDetail.setEmail("testbuddy@tutotialsbuddy.com");
        return userDetail;
    }
}
