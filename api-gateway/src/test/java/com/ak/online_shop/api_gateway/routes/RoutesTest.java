package com.ak.online_shop.api_gateway.routes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for the Routes configuration class.
 * Tests verify that all route beans are properly configured and registered.
 */
@SpringBootTest(classes = {Routes.class})
@TestPropertySource(properties = {
        "server.port=0"
})
class RoutesTest {

    @Autowired
    private Routes routes;

    @Test
    @DisplayName("Routes configuration bean should be created successfully")
    void routesConfigurationShouldBeCreated() {
        assertNotNull(routes, "Routes configuration should not be null");
    }

    @Test
    @DisplayName("Product service route bean should be created")
    void productServiceRouteShouldBeCreated() {
        RouterFunction<ServerResponse> productServiceRoute = routes.productServiceRoute();
        assertNotNull(productServiceRoute, "Product service route should not be null");
    }

    @Test
    @DisplayName("Order service route bean should be created")
    void orderServiceRouteShouldBeCreated() {
        RouterFunction<ServerResponse> orderServiceRoute = routes.orderServiceRoute();
        assertNotNull(orderServiceRoute, "Order service route should not be null");
    }

    @Test
    @DisplayName("Inventory service route bean should be created")
    void inventoryServiceRouteShouldBeCreated() {
        RouterFunction<ServerResponse> inventoryServiceRoute = routes.inventoryServiceRoute();
        assertNotNull(inventoryServiceRoute, "Inventory service route should not be null");
    }

    @Test
    @DisplayName("All route beans should be distinct instances")
    void allRoutesShouldBeDistinctInstances() {
        RouterFunction<ServerResponse> productRoute = routes.productServiceRoute();
        RouterFunction<ServerResponse> orderRoute = routes.orderServiceRoute();
        RouterFunction<ServerResponse> inventoryRoute = routes.inventoryServiceRoute();

        assertNotSame(productRoute, orderRoute, 
                "Product and order routes should be different instances");
        assertNotSame(productRoute, inventoryRoute, 
                "Product and inventory routes should be different instances");
        assertNotSame(orderRoute, inventoryRoute, 
                "Order and inventory routes should be different instances");
    }

    @Test
    @DisplayName("Product service route should have correct toString representation")
    void productServiceRouteShouldHaveCorrectStringRepresentation() {
        RouterFunction<ServerResponse> productRoute = routes.productServiceRoute();
        String routeString = productRoute.toString();
        assertNotNull(routeString, "Route toString should not be null");
        assertFalse(routeString.isEmpty(), "Route toString should not be empty");
    }

    @Test
    @DisplayName("Order service route should have correct toString representation")
    void orderServiceRouteShouldHaveCorrectStringRepresentation() {
        RouterFunction<ServerResponse> orderRoute = routes.orderServiceRoute();
        String routeString = orderRoute.toString();
        assertNotNull(routeString, "Route toString should not be null");
        assertFalse(routeString.isEmpty(), "Route toString should not be empty");
    }

    @Test
    @DisplayName("Inventory service route should have correct toString representation")
    void inventoryServiceRouteShouldHaveCorrectStringRepresentation() {
        RouterFunction<ServerResponse> inventoryRoute = routes.inventoryServiceRoute();
        String routeString = inventoryRoute.toString();
        assertNotNull(routeString, "Route toString should not be null");
        assertFalse(routeString.isEmpty(), "Route toString should not be empty");
    }

    @Test
    @DisplayName("Routes class should have Configuration annotation")
    void routesClassShouldHaveConfigurationAnnotation() {
        assertTrue(Routes.class.isAnnotationPresent(Configuration.class),
                "Routes class should be annotated with @Configuration");
    }

    @Test
    @DisplayName("Product service route method should have Bean annotation")
    void productServiceRouteMethodShouldHaveBeanAnnotation() throws NoSuchMethodException {
        assertTrue(Routes.class.getMethod("productServiceRoute")
                        .isAnnotationPresent(org.springframework.context.annotation.Bean.class),
                "productServiceRoute method should be annotated with @Bean");
    }

    @Test
    @DisplayName("Order service route method should have Bean annotation")
    void orderServiceRouteMethodShouldHaveBeanAnnotation() throws NoSuchMethodException {
        assertTrue(Routes.class.getMethod("orderServiceRoute")
                        .isAnnotationPresent(org.springframework.context.annotation.Bean.class),
                "orderServiceRoute method should be annotated with @Bean");
    }

    @Test
    @DisplayName("Inventory service route method should have Bean annotation")
    void inventoryServiceRouteMethodShouldHaveBeanAnnotation() throws NoSuchMethodException {
        assertTrue(Routes.class.getMethod("inventoryServiceRoute")
                        .isAnnotationPresent(org.springframework.context.annotation.Bean.class),
                "inventoryServiceRoute method should be annotated with @Bean");
    }

    @Test
    @DisplayName("All route methods should return RouterFunction type")
    void allRouteMethodsShouldReturnRouterFunctionType() throws NoSuchMethodException {
        assertEquals(RouterFunction.class, 
                Routes.class.getMethod("productServiceRoute").getReturnType(),
                "productServiceRoute should return RouterFunction");
        assertEquals(RouterFunction.class, 
                Routes.class.getMethod("orderServiceRoute").getReturnType(),
                "orderServiceRoute should return RouterFunction");
        assertEquals(RouterFunction.class, 
                Routes.class.getMethod("inventoryServiceRoute").getReturnType(),
                "inventoryServiceRoute should return RouterFunction");
    }

    @Test
    @DisplayName("Multiple invocations of productServiceRoute should return new instances")
    void multipleInvocationsOfProductServiceRouteShouldReturnNewInstances() {
        RouterFunction<ServerResponse> route1 = routes.productServiceRoute();
        RouterFunction<ServerResponse> route2 = routes.productServiceRoute();
        
        // Each invocation should create a new route instance
        assertNotSame(route1, route2, 
                "Multiple invocations should create new route instances");
    }

    @Test
    @DisplayName("Multiple invocations of orderServiceRoute should return new instances")
    void multipleInvocationsOfOrderServiceRouteShouldReturnNewInstances() {
        RouterFunction<ServerResponse> route1 = routes.orderServiceRoute();
        RouterFunction<ServerResponse> route2 = routes.orderServiceRoute();
        
        assertNotSame(route1, route2, 
                "Multiple invocations should create new route instances");
    }

    @Test
    @DisplayName("Multiple invocations of inventoryServiceRoute should return new instances")
    void multipleInvocationsOfInventoryServiceRouteShouldReturnNewInstances() {
        RouterFunction<ServerResponse> route1 = routes.inventoryServiceRoute();
        RouterFunction<ServerResponse> route2 = routes.inventoryServiceRoute();
        
        assertNotSame(route1, route2, 
                "Multiple invocations should create new route instances");
    }

    @Test
    @DisplayName("Routes class should be a public class")
    void routesClassShouldBePublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(Routes.class.getModifiers()),
                "Routes class should be public");
    }

    @Test
    @DisplayName("All route methods should be public")
    void allRouteMethodsShouldBePublic() throws NoSuchMethodException {
        assertTrue(java.lang.reflect.Modifier.isPublic(
                Routes.class.getMethod("productServiceRoute").getModifiers()),
                "productServiceRoute should be public");
        assertTrue(java.lang.reflect.Modifier.isPublic(
                Routes.class.getMethod("orderServiceRoute").getModifiers()),
                "orderServiceRoute should be public");
        assertTrue(java.lang.reflect.Modifier.isPublic(
                Routes.class.getMethod("inventoryServiceRoute").getModifiers()),
                "inventoryServiceRoute should be public");
    }
}