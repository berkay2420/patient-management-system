package com.pm.api_gateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory
        extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                            @Value("${auth.service.url}") String authServiceURL){
        this.webClient = webClientBuilder.baseUrl(authServiceURL).build();

    }

    @Override
    public GatewayFilter apply(Object config){
        return ((exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if(token==null || !token.startsWith("Bearer ")){
                //return unauthorized if token is null we dont need to send api call with empty token
                //exchange = current request
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClient.get()
                    .uri("/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    //ignore the response body, just check the status code(200,400)
                    //"/validate" method only returns 200 or negative if doesnt send any JSON body
                    .toBodilessEntity()
                    .then(chain.filter(exchange));

        });
    }
}
