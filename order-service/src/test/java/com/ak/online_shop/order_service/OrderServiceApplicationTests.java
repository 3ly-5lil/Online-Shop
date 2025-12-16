package com.ak.online_shop.order_service;

import com.ak.online_shop.order_service.dto.OrderRequest;
import com.ak.online_shop.order_service.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
//import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// TODO: implement the connection test with wire mock using stub instead of testing on direct connection
//@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {
    @LocalServerPort
    private Integer port;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

	@Test
	void shouldCreateOrder() throws JsonProcessingException {
        OrderRequest orderRequest = new OrderRequest(
                null,
                null,
                "product_1",
                new BigDecimal("500.25"),
                2
        );

        InventoryClientStub.stubInventoryCall(orderRequest.skuCode(), orderRequest.quantity());

        RestAssured.given()
                .contentType("application/json")
                .body(mapper.writeValueAsString(orderRequest))
                .when()
                .post("/api/order")
                .then()
                .statusCode(201)
                .body(Matchers.is("Order created successfully"));
	}

}
