package nod.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    public static final String X_SERVICE_NAME = "X-Service-Name";

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/auth-service/**")
                        .filters(f -> f
                                .rewritePath("/auth-service/(?<segment>.*)", "/${segment}")
                                .addRequestHeader(X_SERVICE_NAME, "auth-service")
                        )
                        .uri("http://localhost:9001"))
                .route(r -> r.path("/chat-service/**")
                        .filters(f -> f
                                .rewritePath("/chat-service/(?<segment>.*)", "/${segment}")
                                .addRequestHeader(X_SERVICE_NAME, "chat-service")
                        )
                        .uri("http://localhost:9002"))
                .build();
    }
}
