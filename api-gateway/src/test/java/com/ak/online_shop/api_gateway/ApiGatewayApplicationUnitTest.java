package com.ak.online_shop.api_gateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ApiGatewayApplication class structure and annotations.
 * These tests verify class-level properties without starting the application context.
 */
class ApiGatewayApplicationUnitTest {

    @Test
    @DisplayName("ApiGatewayApplication class should exist")
    void apiGatewayApplicationClassShouldExist() {
        assertNotNull(ApiGatewayApplication.class, "ApiGatewayApplication class should exist");
    }

    @Test
    @DisplayName("ApiGatewayApplication should be annotated with @SpringBootApplication")
    void shouldHaveSpringBootApplicationAnnotation() {
        assertTrue(ApiGatewayApplication.class.isAnnotationPresent(SpringBootApplication.class),
                "ApiGatewayApplication should have @SpringBootApplication annotation");
    }

    @Test
    @DisplayName("ApiGatewayApplication should be a public class")
    void shouldBePublicClass() {
        assertTrue(Modifier.isPublic(ApiGatewayApplication.class.getModifiers()),
                "ApiGatewayApplication should be a public class");
    }

    @Test
    @DisplayName("ApiGatewayApplication should not be abstract")
    void shouldNotBeAbstract() {
        assertFalse(Modifier.isAbstract(ApiGatewayApplication.class.getModifiers()),
                "ApiGatewayApplication should not be abstract");
    }

    @Test
    @DisplayName("ApiGatewayApplication should not be an interface")
    void shouldNotBeInterface() {
        assertFalse(ApiGatewayApplication.class.isInterface(),
                "ApiGatewayApplication should not be an interface");
    }

    @Test
    @DisplayName("ApiGatewayApplication should have a main method")
    void shouldHaveMainMethod() {
        assertDoesNotThrow(() -> {
            Method mainMethod = ApiGatewayApplication.class.getMethod("main", String[].class);
            assertNotNull(mainMethod, "Main method should exist");
        }, "Should have a main method with String[] parameter");
    }

    @Test
    @DisplayName("Main method should be public static void")
    void mainMethodShouldBePublicStaticVoid() throws NoSuchMethodException {
        Method mainMethod = ApiGatewayApplication.class.getMethod("main", String[].class);
        
        assertTrue(Modifier.isPublic(mainMethod.getModifiers()),
                "Main method should be public");
        assertTrue(Modifier.isStatic(mainMethod.getModifiers()),
                "Main method should be static");
        assertEquals(void.class, mainMethod.getReturnType(),
                "Main method should return void");
    }

    @Test
    @DisplayName("Main method should accept String array as parameter")
    void mainMethodShouldAcceptStringArrayParameter() throws NoSuchMethodException {
        Method mainMethod = ApiGatewayApplication.class.getMethod("main", String[].class);
        Class<?>[] parameterTypes = mainMethod.getParameterTypes();
        
        assertEquals(1, parameterTypes.length,
                "Main method should have exactly one parameter");
        assertEquals(String[].class, parameterTypes[0],
                "Main method parameter should be String[]");
    }

    @Test
    @DisplayName("ApiGatewayApplication should have a public no-args constructor")
    void shouldHavePublicNoArgsConstructor() {
        assertDoesNotThrow(() -> {
            ApiGatewayApplication instance = new ApiGatewayApplication();
            assertNotNull(instance, "Should be able to create an instance");
        }, "Should have a public no-args constructor");
    }

    @Test
    @DisplayName("ApiGatewayApplication should be in correct package")
    void shouldBeInCorrectPackage() {
        assertEquals("com.ak.online_shop.api_gateway", 
                ApiGatewayApplication.class.getPackageName(),
                "ApiGatewayApplication should be in com.ak.online_shop.api_gateway package");
    }

    @Test
    @DisplayName("Class name should be ApiGatewayApplication")
    void classNameShouldBeCorrect() {
        assertEquals("ApiGatewayApplication", 
                ApiGatewayApplication.class.getSimpleName(),
                "Class simple name should be ApiGatewayApplication");
    }

    @Test
    @DisplayName("ApiGatewayApplication should extend Object")
    void shouldExtendObject() {
        assertEquals(Object.class, ApiGatewayApplication.class.getSuperclass(),
                "ApiGatewayApplication should directly extend Object");
    }

    @Test
    @DisplayName("ApiGatewayApplication should not implement any interfaces")
    void shouldNotImplementInterfaces() {
        assertEquals(0, ApiGatewayApplication.class.getInterfaces().length,
                "ApiGatewayApplication should not implement any interfaces");
    }

    @Test
    @DisplayName("Main method should have proper parameter name")
    void mainMethodParameterShouldHaveProperName() throws NoSuchMethodException {
        Method mainMethod = ApiGatewayApplication.class.getMethod("main", String[].class);
        // Parameter names are available with -parameters compiler flag
        // This test verifies the method signature is correct
        assertNotNull(mainMethod.getParameters(), 
                "Main method should have parameters array");
        assertEquals(1, mainMethod.getParameterCount(),
                "Main method should have exactly one parameter");
    }

    @Test
    @DisplayName("SpringBootApplication annotation should have default configuration")
    void springBootApplicationAnnotationShouldHaveDefaultConfiguration() {
        SpringBootApplication annotation = ApiGatewayApplication.class
                .getAnnotation(SpringBootApplication.class);
        
        assertNotNull(annotation, "@SpringBootApplication annotation should be present");
        // Verify default values
        assertEquals(0, annotation.exclude().length,
                "Should not exclude any auto-configurations by default");
        assertEquals(0, annotation.excludeName().length,
                "Should not exclude any auto-configurations by name by default");
    }

    @Test
    @DisplayName("Class should only have one method (main)")
    void classShouldOnlyHaveMainMethod() {
        Method[] declaredMethods = ApiGatewayApplication.class.getDeclaredMethods();
        assertEquals(1, declaredMethods.length,
                "ApiGatewayApplication should only declare the main method");
        assertEquals("main", declaredMethods[0].getName(),
                "The only declared method should be 'main'");
    }

    @Test
    @DisplayName("Class should not have any declared fields")
    void classShouldNotHaveAnyFields() {
        assertEquals(0, ApiGatewayApplication.class.getDeclaredFields().length,
                "ApiGatewayApplication should not have any declared fields");
    }

    @Test
    @DisplayName("Multiple instances can be created")
    void multipleInstancesCanBeCreated() {
        ApiGatewayApplication instance1 = new ApiGatewayApplication();
        ApiGatewayApplication instance2 = new ApiGatewayApplication();
        
        assertNotNull(instance1, "First instance should not be null");
        assertNotNull(instance2, "Second instance should not be null");
        assertNotSame(instance1, instance2, 
                "Multiple instances should be different objects");
    }
}