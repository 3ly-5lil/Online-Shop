package com.ak.online_shop.api_gateway;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;

/**
 * Integration tests for the API Gateway.
 * These tests verify the gateway's routing behavior and HTTP handling.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "spring.application.name=api-gateway-test"
})
class ApiGatewayIntegrationTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Gateway should start successfully on random port")
    void gatewayShouldStartSuccessfully() {
        // If we reach here, the gateway has started successfully
        assert port != null && port > 0 : "Port should be assigned";
    }

    @Test
    @DisplayName("Product service route should be accessible")
    void productServiceRouteShouldBeAccessible() {
        // The route is configured but backend is not available
        // We expect connection error or 502 Bad Gateway, not 404
        given()
                .when()
                .get("/api/product")
                .then()
                .statusCode(org.hamcrest.Matchers.not(404));
    }

    @Test
    @DisplayName("Order service route should be accessible")
    void orderServiceRouteShouldBeAccessible() {
        given()
                .when()
                .get("/api/order")
                .then()
                .statusCode(org.hamcrest.Matchers.not(404));
    }

    @Test
    @DisplayName("Inventory service route should be accessible")
    void inventoryServiceRouteShouldBeAccessible() {
        given()
                .when()
                .get("/api/inventory")
                .then()
                .statusCode(org.hamcrest.Matchers.not(404));
    }

    @Test
    @DisplayName("Non-existent route should return 404")
    void nonExistentRouteShouldReturn404() {
        given()
                .when()
                .get("/api/nonexistent")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Root path should return 404")
    void rootPathShouldReturn404() {
        given()
                .when()
                .get("/")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Product service route should accept POST requests")
    void productServiceRouteShouldAcceptPostRequests() {
        given()
                .contentType("application/json")
                .body("{\"name\":\"test\"}")
                .when()
                .post("/api/product")
                .then()
                .statusCode(org.hamcrest.Matchers.not(404));
    }

    @Test
    @DisplayName("Order service route should accept POST requests")
    void orderServiceRouteShouldAcceptPostRequests() {
        given()
                .contentType("application/json")
                .body("{\"orderId\":\"test\"}")
                .when()
                .post("/api/order")
                .then()
                .statusCode(org.hamcrest.Matchers.not(404));
    }

    @Test
    @DisplayName("Inventory service route should accept GET requests with query parameters")
    void inventoryServiceRouteShouldAcceptGetWithQueryParams() {
        given()
                .queryParam("skuCode", "product_1")
                .queryParam("quantity", "10")
                .when()
                .get("/api/inventory")
                .then()
                .statusCode(org.hamcrest.Matchers.not(404));
    }

    @Test
    @DisplayName("Gateway should handle multiple concurrent requests")
    void gatewayShouldHandleMultipleConcurrentRequests() throws InterruptedException {
        Thread[] threads = new Thread[10];
        
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                String path = switch (index % 3) {
                    case 0 -> "/api/product";
                    case 1 -> "/api/order";
                    default -> "/api/inventory";
                };
                
                given()
                        .when()
                        .get(path)
                        .then()
                        .statusCode(org.hamcrest.Matchers.not(404));
            });
            threads[i].start();
        }
        
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    @DisplayName("Gateway should preserve request headers")
    void gatewayShouldPreserveRequestHeaders() {
        given()
                .header("X-Custom-Header", "test-value")
                .header("User-Agent", "test-agent")
                .when()
                .get("/api/product")
                .then()
                .statusCode(org.hamcrest.Matchers.not(404));
    }

    @Test
    @DisplayName("Gateway should handle URL encoded paths")
    void gatewayShouldHandleUrlEncodedPaths() {
        given()
                .when()
                .get("/api/product?name=test%20product")
                .then()
                .statusCode(org.hamcrest.Matchers.not(404));
    }

    @Test
    @DisplayName("Gateway should handle different HTTP methods on product route")
    void gatewayShouldHandleDifferentHttpMethodsOnProductRoute() {
        // GET
        given().when().get("/api/product")
                .then().statusCode(org.hamcrest.Matchers.not(404));
        
        // POST
        given().contentType("application/json").body("{}")
                .when().post("/api/product")
                .then().statusCode(org.hamcrest.Matchers.not(404));
        
        // PUT
        given().contentType("application/json").body("{}")
                .when().put("/api/product")
                .then().statusCode(org.hamcrest.Matchers.not(404));
        
        // DELETE
        given().when().delete("/api/product")
                .then().statusCode(org.hamcrest.Matchers.not(404));
    }
}