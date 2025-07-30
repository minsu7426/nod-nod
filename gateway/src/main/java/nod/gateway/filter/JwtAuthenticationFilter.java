package nod.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nod.core.error.CommonError;
import nod.core.exception.BusinessException;
import nod.gateway.config.FilterConfig;
import nod.util.jwt.TokenProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
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

    private static final String HEADER_USER_ID = "X-User-Id";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final Map<String, List<String>> WHITE_LIST = Map.of(
            "auth-service", List.of("/login", "/auth/v1/register"),
            "chat-service", List.of("/hello")
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        String serviceName = exchange.getRequest().getHeaders().getFirst(FilterConfig.X_SERVICE_NAME);

        log.debug("service: {}, path: {}", serviceName, path);

        if (serviceName == null) {
            return Mono.error(new BusinessException(CommonError.BAD_REQUEST));
        }

        if (isWhitelisted(serviceName, path)) {
            return chain.filter(exchange);
        };

        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            return Mono.error(new BusinessException(CommonError.FORBIDDEN));
        }

        String token = tokenProvider.parseBearerToken(header);
        if (tokenProvider.isTokenExpired(token)) {
            return Mono.error(new BusinessException(CommonError.EXPIRED_TOKEN));
        }

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header(HEADER_USER_ID, tokenProvider.getUsername(token))
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean isWhitelisted(String serviceName, String path) {
        return WHITE_LIST.getOrDefault(serviceName, List.of()).contains(path);
    }
}
