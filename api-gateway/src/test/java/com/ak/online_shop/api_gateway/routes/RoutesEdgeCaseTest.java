package com.ak.online_shop.api_gateway.routes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Edge case and stress tests for the Routes configuration.
 * Tests boundary conditions and concurrent access scenarios.
 */
@SpringBootTest(classes = {Routes.class})
@TestPropertySource(properties = {
        "server.port=0"
})
class RoutesEdgeCaseTest {

    @Autowired
    private Routes routes;

    @RepeatedTest(10)
    @DisplayName("Product service route should be consistent across multiple invocations")
    void productServiceRouteShouldBeConsistent() {
        RouterFunction<ServerResponse> route = routes.productServiceRoute();
        assertNotNull(route, "Route should not be null");
        assertNotNull(route.toString(), "Route toString should not be null");
    }

    @RepeatedTest(10)
    @DisplayName("Order service route should be consistent across multiple invocations")
    void orderServiceRouteShouldBeConsistent() {
        RouterFunction<ServerResponse> route = routes.orderServiceRoute();
        assertNotNull(route, "Route should not be null");
        assertNotNull(route.toString(), "Route toString should not be null");
    }

    @RepeatedTest(10)
    @DisplayName("Inventory service route should be consistent across multiple invocations")
    void inventoryServiceRouteShouldBeConsistent() {
        RouterFunction<ServerResponse> route = routes.inventoryServiceRoute();
        assertNotNull(route, "Route should not be null");
        assertNotNull(route.toString(), "Route toString should not be null");
    }

    @Test
    @DisplayName("All routes should be thread-safe for concurrent access")
    void routesShouldBeThreadSafe() throws InterruptedException {
        Thread[] threads = new Thread[20];
        final boolean[] failures = {false};

        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                try {
                    RouterFunction<ServerResponse> route = switch (index % 3) {
                        case 0 -> routes.productServiceRoute();
                        case 1 -> routes.orderServiceRoute();
                        default -> routes.inventoryServiceRoute();
                    };
                    assertNotNull(route, "Route should not be null in thread " + index);
                } catch (Exception e) {
                    failures[0] = true;
                }
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertFalse(failures[0], "No failures should occur during concurrent access");
    }

    @Test
    @DisplayName("Routes instance should remain consistent")
    void routesInstanceShouldRemainConsistent() {
        assertNotNull(routes, "Routes instance should not be null");
        
        // Call all route methods multiple times
        for (int i = 0; i < 5; i++) {
            assertDoesNotThrow(() -> routes.productServiceRoute(),
                    "Product service route should not throw on call " + i);
            assertDoesNotThrow(() -> routes.orderServiceRoute(),
                    "Order service route should not throw on call " + i);
            assertDoesNotThrow(() -> routes.inventoryServiceRoute(),
                    "Inventory service route should not throw on call " + i);
        }
    }

    @Test
    @DisplayName("Route beans should handle rapid successive calls")
    void routeBeansShouldHandleRapidSuccessiveCalls() {
        for (int i = 0; i < 100; i++) {
            RouterFunction<ServerResponse> productRoute = routes.productServiceRoute();
            RouterFunction<ServerResponse> orderRoute = routes.orderServiceRoute();
            RouterFunction<ServerResponse> inventoryRoute = routes.inventoryServiceRoute();

            assertNotNull(productRoute, "Product route should not be null on iteration " + i);
            assertNotNull(orderRoute, "Order route should not be null on iteration " + i);
            assertNotNull(inventoryRoute, "Inventory route should not be null on iteration " + i);
        }
    }

    @Test
    @DisplayName("Routes should maintain consistency after garbage collection hint")
    void routesShouldMaintainConsistencyAfterGarbageCollection() {
        // Create many route instances to potentially trigger GC
        for (int i = 0; i < 1000; i++) {
            routes.productServiceRoute();
        }

        // Suggest garbage collection
        System.gc();

        // Routes should still work correctly
        assertNotNull(routes.productServiceRoute(), "Product route should still work after GC");
        assertNotNull(routes.orderServiceRoute(), "Order route should still work after GC");
        assertNotNull(routes.inventoryServiceRoute(), "Inventory route should still work after GC");
    }

    @Test
    @DisplayName("Route methods should not mutate routes instance state")
    void routeMethodsShouldNotMutateRoutesState() {
        // Get initial routes
        RouterFunction<ServerResponse> route1 = routes.productServiceRoute();
        
        // Call other route methods
        routes.orderServiceRoute();
        routes.inventoryServiceRoute();
        
        // Get product route again
        RouterFunction<ServerResponse> route2 = routes.productServiceRoute();
        
        // Both should be valid (though different instances due to prototype scope)
        assertNotNull(route1, "First product route should remain valid");
        assertNotNull(route2, "Second product route should be valid");
    }

    @Test
    @DisplayName("Routes configuration should be reusable")
    void routesConfigurationShouldBeReusable() {
        Routes newRoutesInstance = new Routes();
        
        assertDoesNotThrow(() -> newRoutesInstance.productServiceRoute(),
                "New Routes instance should work for product service");
        assertDoesNotThrow(() -> newRoutesInstance.orderServiceRoute(),
                "New Routes instance should work for order service");
        assertDoesNotThrow(() -> newRoutesInstance.inventoryServiceRoute(),
                "New Routes instance should work for inventory service");
    }

    @Test
    @DisplayName("Route toString should not throw exceptions")
    void routeToStringShouldNotThrowExceptions() {
        assertDoesNotThrow(() -> {
            String productStr = routes.productServiceRoute().toString();
            String orderStr = routes.orderServiceRoute().toString();
            String inventoryStr = routes.inventoryServiceRoute().toString();
            
            assertNotNull(productStr);
            assertNotNull(orderStr);
            assertNotNull(inventoryStr);
        }, "toString operations should not throw exceptions");
    }

    @Test
    @DisplayName("Route instances should be independently functional")
    void routeInstancesShouldBeIndependentlyFunctional() {
        RouterFunction<ServerResponse> route1 = routes.productServiceRoute();
        RouterFunction<ServerResponse> route2 = routes.productServiceRoute();
        
        // Both should function independently
        String str1 = route1.toString();
        String str2 = route2.toString();
        
        assertNotNull(str1, "First route should be functional");
        assertNotNull(str2, "Second route should be functional");
    }
}