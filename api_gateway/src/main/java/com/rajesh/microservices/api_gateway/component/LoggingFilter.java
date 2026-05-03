package com.rajesh.microservices.api_gateway.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter implements GlobalFilter, Ordered {

    @Override
    public int getOrder() {
        return -1; // AFTER auth
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        long start = System.currentTimeMillis();

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        log.info("Incoming request {} {}", method, path);

        return chain.filter(exchange)
                .doOnSuccess(aVoid -> {
                    long time = System.currentTimeMillis() - start;
                    int status = exchange.getResponse().getStatusCode().value();

                    log.info("Completed request {} {} status={} time={}ms",
                            method, path, status, time);
                });
    }
}
