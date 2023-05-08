package msa.customer.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import msa.customer.service.JoinService;
import msa.customer.service.ParseJwtService;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class JoinCheckInterceptor implements HandlerInterceptor {

    private final ParseJwtService parseJwtService;
    private final JoinService joinService;

    public JoinCheckInterceptor(ParseJwtService parseJwtService, JoinService joinService) {
        this.parseJwtService = parseJwtService;
        this.joinService = joinService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("Authorization");
        String cognitoUsername = parseJwtService.getCognitoUsernameFromJwt(jwt);
        if(!joinService.checkJoinedMember(cognitoUsername)){
            String email = parseJwtService.getEmailFromJwt(jwt);
            joinService.joinMember(cognitoUsername, email);
            log.info("Create member: id={}, email={}", cognitoUsername, email);
        }
        return true;
    }
}
