package com.ak.online_shop.api_gateway.routes;

import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.*;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

@Configuration
public class Routes {
    /**
     * Registers a gateway route named "product-service" that forwards requests with path "/api/product" to the backend at http://localhost:8080.
     *
     * @return a RouterFunction that routes requests matching "/api/product" to the configured backend URI
     */
    @Bean
    public RouterFunction<ServerResponse> productServiceRoute() {
        return GatewayRouterFunctions.route("product-service")
                .route(RequestPredicates.path("/api/product"), http())
                .before(uri("http://localhost:8080"))
                .build();
    }
    /**
     * Registers a gateway route named "order-service" that handles incoming requests for the orders API.
     *
     * The route matches requests with path "/api/order" and forwards them to the order backend at http://localhost:8081.
     *
     * @return a RouterFunction that routes matching requests to the order backend
     */
    @Bean
    public RouterFunction<ServerResponse> orderServiceRoute() {
        return GatewayRouterFunctions.route("order-service")
                .route(RequestPredicates.path("/api/order"), http())
                .before(uri("http://localhost:8081"))
                .build();
    }

    /**
     * Registers a gateway route that forwards requests under "/api/inventory" to the inventory backend.
     *
     * @return a RouterFunction that routes requests matching "/api/inventory" to the inventory service at http://localhost:8082
     */
    @Bean
    public RouterFunction<ServerResponse> inventoryServiceRoute() {
        return GatewayRouterFunctions.route("inventory-service")
                .route(RequestPredicates.path("/api/inventory"), http())
                .before(uri("http://localhost:8082"))
                .build();
    }
}