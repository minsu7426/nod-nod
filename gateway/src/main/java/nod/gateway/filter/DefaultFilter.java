package nod.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//@Component
//public class DefaultFilter implements GlobalFilter {
public class DefaultFilter {

//    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("DefaultFilter.filter");
        System.out.println("exchange = " + exchange);
        System.out.println("chain = " + chain);
        return chain.filter(exchange);
    }
}
