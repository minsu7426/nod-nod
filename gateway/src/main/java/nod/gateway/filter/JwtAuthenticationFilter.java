package nod.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nod.gateway.config.FilterConfig;
import nod.util.jwt.TokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final TokenProvider tokenProvider;

    public static final String BEARER_PREFIX = "Bearer ";
    public static final Map<String, List<String>> WHITE_LIST = Map.of(
            "auth-service", List.of("/login", "/auth/v1/register"),
            "chat-service", List.of("/chat")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String serviceName = exchange.getRequest().getHeaders().getFirst(FilterConfig.X_SERVICE_NAME);

        log.debug("service: {}, path: {}", serviceName, path);

        if (isWhitelisted(serviceName, path)) {
            return chain.filter(exchange);
        };

        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String parseBearerToken = tokenProvider.parseBearerToken(header);
        System.out.println("parseBearerToken = " + parseBearerToken);

//        String token = header.substring(BEARER_PREFIX.length());

        // TODO: JWT 검증
        // if (!jwtUtil.validate(token)) return 401

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", "mock-user") // 실제는 claim에서 userId 추출
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean isWhitelisted(String serviceName, String path) {
        return WHITE_LIST.getOrDefault(serviceName, List.of()).contains(path);
    }
}
