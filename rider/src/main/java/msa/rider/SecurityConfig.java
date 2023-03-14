package msa.rider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String SIGNUP_URL = "/api/users/sign-up";
    public static final String SIGNIN_URL = "/api/users/sign-in";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        List<String> permitAllEndpointList = Arrays.asList(SIGNUP_URL, SIGNIN_URL);

        http.cors().and().csrf().disable()
                .authorizeHttpRequests((authz) ->
                        authz.requestMatchers(permitAllEndpointList.toArray(new String[permitAllEndpointList.size()]))
                                .permitAll().anyRequest().authenticated())
                .oauth2ResourceServer().jwt();
        return http.getOrBuild();
    }
}
