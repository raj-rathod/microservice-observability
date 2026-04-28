package com.rajesh.microservices.api_gateway.exception;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Order(-1)
public class RateLimitExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {

        if (ex instanceof org.springframework.cloud.gateway.support.NotFoundException ||
                ex.getMessage().contains("429") ||
                ex.getClass().getSimpleName().contains("RateLimit")) {

            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

            String body = """
            {
              "status": 429,
              "message": "Too many requests. Please slow down.",
              "retryAfterSeconds": 10
            }
            """;

            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(body.getBytes(StandardCharsets.UTF_8));

            return exchange.getResponse().writeWith(Mono.just(buffer));
        }

        return Mono.error(ex);
    }
}