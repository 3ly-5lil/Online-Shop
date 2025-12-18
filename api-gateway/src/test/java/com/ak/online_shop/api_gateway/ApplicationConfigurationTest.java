package com.ak.online_shop.api_gateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for application configuration and properties.
 * Validates that application.yaml settings are correctly loaded.
 */
@SpringBootTest
@TestPropertySource(properties = {
        "server.port=0"
})
class ApplicationConfigurationTest {

    @Autowired
    private Environment environment;

    @Test
    @DisplayName("Environment should be autowired successfully")
    void environmentShouldBeAutowired() {
        assertNotNull(environment, "Environment should be autowired");
    }

    @Test
    @DisplayName("Application name should be configured as api-gateway")
    void applicationNameShouldBeConfigured() {
        String applicationName = environment.getProperty("spring.application.name");
        assertEquals("api-gateway", applicationName,
                "Application name should be 'api-gateway'");
    }

    @Test
    @DisplayName("Server port configuration should be accessible")
    void serverPortShouldBeAccessible() {
        String serverPort = environment.getProperty("server.port");
        assertNotNull(serverPort, "Server port property should be set");
    }

    @Test
    @DisplayName("Spring application name property should not be null")
    void springApplicationNameShouldNotBeNull() {
        String name = environment.getProperty("spring.application.name");
        assertNotNull(name, "spring.application.name should not be null");
        assertFalse(name.isEmpty(), "spring.application.name should not be empty");
    }

    @Test
    @DisplayName("Active profiles should be accessible")
    void activeProfilesShouldBeAccessible() {
        String[] profiles = environment.getActiveProfiles();
        assertNotNull(profiles, "Active profiles should not be null");
    }

    @Test
    @DisplayName("Default profiles should be accessible")
    void defaultProfilesShouldBeAccessible() {
        String[] defaultProfiles = environment.getDefaultProfiles();
        assertNotNull(defaultProfiles, "Default profiles should not be null");
    }

    @Test
    @DisplayName("Environment should contain system properties")
    void environmentShouldContainSystemProperties() {
        String javaVersion = environment.getProperty("java.version");
        assertNotNull(javaVersion, "Java version should be accessible from environment");
    }

    @Test
    @DisplayName("Environment should resolve property placeholders")
    void environmentShouldResolvePropertyPlaceholders() {
        String resolved = environment.resolvePlaceholders("${spring.application.name}");
        assertEquals("api-gateway", resolved,
                "Property placeholders should be resolved correctly");
    }

    @Test
    @DisplayName("Environment should indicate if property exists")
    void environmentShouldIndicateIfPropertyExists() {
        assertTrue(environment.containsProperty("spring.application.name"),
                "spring.application.name property should exist");
        assertFalse(environment.containsProperty("non.existent.property"),
                "Non-existent property should return false");
    }

    @Test
    @DisplayName("Required property should be retrievable")
    void requiredPropertyShouldBeRetrievable() {
        assertDoesNotThrow(() -> {
            String name = environment.getRequiredProperty("spring.application.name");
            assertEquals("api-gateway", name);
        }, "Required property should be retrievable without exception");
    }

    @Test
    @DisplayName("Spring Boot version should be accessible")
    void springBootVersionShouldBeAccessible() {
        String springBootVersion = environment.getProperty("spring-boot.version");
        // May or may not be set, but accessing it should not throw an exception
        assertDoesNotThrow(() -> environment.getProperty("spring-boot.version"),
                "Should be able to access spring-boot.version property");
    }
}