package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Microservices Basics
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson10_MicroservicesBasics {
    public static void main(String[] args) {
        System.out.println("""
                === MICROSERVICES BASICS ===

                1. WHAT ARE MICROSERVICES?
                   - Architectural style: small, independent services
                   - Each service owns its data and logic
                   - Communicate via HTTP/REST or messaging
                   - Deployed independently

                2. SERVICE COMMUNICATION
                   Synchronous (REST):
                     RestTemplate restTemplate = new RestTemplate();
                     User user = restTemplate.getForObject("http://user-service/users/1", User.class);

                   Asynchronous (Messaging):
                     @RabbitListener(queues = "order-queue")
                     public void handleOrderCreated(OrderEvent event) { ... }

                3. API GATEWAY
                   - Single entry point for all clients
                   - Handles: routing, authentication, rate limiting, logging
                   - Spring Cloud Gateway or Netflix Zuul

                4. SERVICE DISCOVERY
                   - Services register themselves on startup
                   - Clients discover services by name, not IP
                   - Netflix Eureka or Consul

                5. CONFIGURATION MANAGEMENT
                   - Spring Cloud Config Server
                   - Centralized configuration for all services
                   - Supports Git backend for versioning

                BACKEND REAL-WORLD EXAMPLE:
                   - E-commerce: user-service, order-service, payment-service, inventory-service
                   - API Gateway routes /api/users to user-service
                   - Order service calls payment and inventory via REST
                   - Events published to RabbitMQ for async processing

                COMMON MISTAKES:
                   - Distributed monolith (services too tightly coupled)
                   - Chatty communication (too many service calls)
                   - No circuit breaker (cascading failures)
                   - Shared database between services
                   - Not handling eventual consistency
                """);
    }
}
