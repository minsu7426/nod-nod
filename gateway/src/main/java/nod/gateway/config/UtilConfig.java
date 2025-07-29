package nod.gateway.config;

import nod.util.jwt.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UtilConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access.seconds}")
    private long accessTokenSeconds;

    @Bean
    public TokenProvider tokenProvider() {
        return new TokenProvider(secret, accessTokenSeconds);
    }
}
