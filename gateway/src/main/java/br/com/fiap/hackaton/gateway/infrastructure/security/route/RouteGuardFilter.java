package br.com.fiap.hackaton.gateway.infrastructure.security.route;

import br.com.fiap.hackaton.gateway.infrastructure.autenticacao.AutenticacaoMessagingGateway;
import br.com.fiap.hackaton.gateway.infrastructure.autenticacao.dto.CheckDto;
import br.com.fiap.hackaton.gateway.infrastructure.security.route.properties.RouteConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class RouteGuardFilter implements GatewayFilter {

    private final AutenticacaoMessagingGateway autenticacaoMessagingGateway;
    private final RouteConfig routeConfig;

    public RouteGuardFilter(
            AutenticacaoMessagingGateway autenticacaoMessagingGateway,
            RouteConfig routeConfig) {
        this.autenticacaoMessagingGateway = autenticacaoMessagingGateway;
        this.routeConfig = routeConfig;
    }

    private boolean isAuthorizationValid(String authorizationHeader) {


        ResponseEntity<CheckDto> response = null;

        try {
            response = this.autenticacaoMessagingGateway.check(
                    authorizationHeader, MessageBuilder.withPayload("").build());

        } catch (Exception e) {
            return false;
        }

        if(response == null) {
            return false;
        }

        CheckDto check = response.getBody();

        if(check == null) {
            return false;
        }

        return check.status();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (!request.getHeaders().containsKey("Authorization")) {
            return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
        };

        String authorizationHeader = request.getHeaders().get("Authorization").get(0);

        if (!this.isAuthorizationValid(authorizationHeader)) {
            return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
        }


        String url = request.getURI().getPath();

        this.routeConfig.isSecureRoute(url);

        return chain.filter(exchange);
    }
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);

        return response.setComplete();
    }

}