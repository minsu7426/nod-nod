package nod.gateway.filter;

import lombok.RequiredArgsConstructor;
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

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final TokenProvider tokenProvider;

    public static final String BEARER_PREFIX = "Bearer ";
    public static final List<String> WHITE_LIST_URI = List.of(
            "/login", "/auth/v1/register"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (isWhitelisted(path)) {
            System.out.println("whitelisted path = " + path);
            return chain.filter(exchange);
        };

        String header = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        System.out.println("header = " + header);
        String parseBearerToken = tokenProvider.parseBearerToken(header);
        System.out.println("parseBearerToken = " + parseBearerToken);

        if (header == null || !header.startsWith(BEARER_PREFIX)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

//        String token = header.substring(BEARER_PREFIX.length());

        // TODO: JWT 검증
        // if (!jwtUtil.validate(token)) return 401

        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                .header("X-User-Id", "mock-user") // 실제는 claim에서 userId 추출
                .build();

        return chain.filter(exchange.mutate().request(mutatedRequest).build());
    }

    private boolean isWhitelisted(String path) {
        return WHITE_LIST_URI.stream().anyMatch(uri -> uri.equals(path));
    }
}
