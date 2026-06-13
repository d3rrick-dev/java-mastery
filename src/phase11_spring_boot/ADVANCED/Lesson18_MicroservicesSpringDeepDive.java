package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Microservices with Spring Deep Dive
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Service discovery
 * 2. API Gateway
 * 3. OpenFeign
 * 4. Configuration server
 * 5. Distributed tracing
 * 6. Inter-service communication
 * 7. SAGA pattern overview
 */

public class Lesson18_MicroservicesSpringDeepDive {
    public static void main(String[] args) {
        System.out.println("""
            === MICROSERVICES WITH SPRING DEEP DIVE ===
            
            1. SERVICE DISCOVERY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Service discovery allows services to find each other
               dynamically without hardcoded addresses.
            
               WHY IT EXISTS:
               - Dynamic service locations
               - Load balancing
               - Health awareness
            
               INTERNAL MECHANICS:
                   - ServiceRegistry holds service instances
                   - Registration binds service to registry
                   - Discovery finds available instances
                   - Heartbeat maintains registration
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableDiscoveryClient
                   public class DiscoveryConfig {
                       @Bean
                       public DiscoveryClient discoveryClient() {
                           return new EurekaDiscoveryClient();
                       }
                   }
                   
                   // application.yml
                   spring:
                     application:
                       name: user-service
                     cloud:
                       discovery:
                         client:
                           service-id: user-service
                   eureka:
                     client:
                       service-url:
                         defaultZone: http://eureka-server:8761/eureka/
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservices architecture:
                   - Eureka server for service registry
                   - Each service registers on startup
                   - Services discover each other by name
                   - Load balancing across instances
            
               INTERVIEW QUESTION:
                   "How does service discovery work in Spring Cloud?
                   What is the difference between Eureka and Consul?"
            
               COMMON MISTAKES:
                   - Not handling service registry failure
                   - Not configuring health checks
                   - Not considering network partitions
            
               PERFORMANCE & SCALABILITY:
                   - Service registry is critical infrastructure
                   - Consider multiple registry instances
                   - Cache discovery results
            
            ─────────────────────────────────────────────────────────────────────
            
            2. API GATEWAY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               API Gateway is a single entry point for all clients,
               handling cross-cutting concerns.
            
               WHY IT EXISTS:
               - Single entry point
               - Cross-cutting concerns
               - Security boundary
               - Request/response transformation
            
               INTERNAL MECHANICS:
                   - RouteLocator defines routes
                   - Predicate determines route matching
                   - Filter transforms requests/responses
                   - LoadBalancerClient routes to services
            
               SIMPLE EXAMPLE:
                   @Bean
                   public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
                       return builder.routes()
                           .route("users", r -> r.path("/api/users/**")
                               .filters(f -> f.stripPrefix(2)
                                   .addRequestHeader("X-User-Header", "value"))
                               .uri("lb://user-service"))
                           .route("orders", r -> r.path("/api/orders/**")
                               .filters(f -> f.hystrix(c -> c.setName("orderService")))
                               .uri("lb://order-service"))
                           .build();
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production API gateway:
                   - Rate limiting per API key
                   - JWT validation
                   - Request/response logging
                   - Circuit breaker for services
                   - Response caching
            
               INTERVIEW QUESTION:
                   "What are the responsibilities of an API gateway?
                   How do you implement rate limiting in the gateway?"
            
               COMMON MISTAKES:
                   - Gateway becoming bottleneck
                   - Not handling gateway failure
                   - Too much logic in gateway
            
               PERFORMANCE & SCALABILITY:
                   - Gateway is critical path
                   - Consider multiple gateway instances
                   - Use async processing
            
            ─────────────────────────────────────────────────────────────────────
            
            3. OPENFEIGN
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               OpenFeign provides declarative REST client, making
               service-to-service calls simple.
            
               WHY IT EXISTS:
               - Simple REST client
               - Integration with Spring
               - Load balancing support
            
               INTERNAL MECHANICS:
                   - FeignClientFactoryBean creates clients
                   - RequestInterceptor adds headers
                   - LoadBalancerClient resolves service names
                   - ErrorDecoder handles errors
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableFeignClients
                   public class FeignConfig {
                       @Bean
                       public Logger.Level feignLoggerLevel() {
                           return Logger.Level.FULL;
                       }
                   }
                   
                   @FeignClient(name = "user-service", 
                               configuration = FeignConfig.class)
                   public interface UserServiceClient {
                       @GetMapping("/users/{id}")
                       User getUser(@PathVariable("id") Long id);
                       
                       @PostMapping("/users")
                       User createUser(@RequestBody User user);
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An order service calling user service:
                   - Feign client for user service
                   - Circuit breaker on Feign calls
                   - Retry on transient errors
                   - Load balancing across instances
            
               INTERVIEW QUESTION:
                   "How do you handle errors in Feign clients?
                   What is the difference between Feign and RestTemplate?"
            
               COMMON MISTAKES:
                   - Not handling Feign exceptions
                   - Not configuring timeouts
                   - Not using circuit breaker
            
               PERFORMANCE & SCALABILITY:
                   - Feign adds overhead
                   - Consider connection pooling
                   - Monitor Feign metrics
            
            ─────────────────────────────────────────────────────────────────────
            
            4. CONFIGURATION SERVER
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Spring Cloud Config provides centralized configuration
               management for all services.
            
               WHY IT EXISTS:
               - Centralized configuration
               - Environment-specific settings
               - Dynamic refresh
            
               INTERNAL MECHANICS:
                   - EnvironmentRepository holds config
                   - PropertySourceLocator finds properties
                   - RefreshScope enables dynamic refresh
                   - Actuator /refresh endpoint
            
               SIMPLE EXAMPLE:
                   // Config Server
                   @SpringBootApplication
                   @EnableConfigServer
                   public class ConfigServer {
                       public static void main(String[] args) {
                           SpringApplication.run(ConfigServer.class, args);
                       }
                   }
                   
                   // application.yml (config repo)
                   user-service:
                     database:
                       url: jdbc:postgresql://user-db:5432/users
                       pool-size: 10
                   
                   // Client
                   @RefreshScope
                   @Component
                   public class DatabaseConfig {
                       @Value("${user-service.database.url}")
                       private String url;
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservices configuration:
                   - Git backend for config versioning
                   - Profile-specific configs
                   - Encryption for secrets
                   - Dynamic refresh without restart
            
               INTERVIEW QUESTION:
                   "How do you secure configuration in Spring Cloud Config?
                   What is the difference between @RefreshScope and @ConfigurationProperties?"
            
               COMMON MISTAKES:
                   - Not encrypting sensitive config
                   - Not handling config server failure
                   - Not testing config changes
            
               PERFORMANCE & SCALABILITY:
                   - Config server is critical
                   - Consider caching
                   - Monitor config refresh
            
            ─────────────────────────────────────────────────────────────────────
            
            5. DISTRIBUTED TRACING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Distributed tracing tracks requests across service
               boundaries for debugging and performance analysis.
            
               WHY IT EXISTS:
               - Debug distributed systems
               - Performance analysis
               - Error tracking
            
               INTERNAL MECHANICS:
                   - Tracer creates spans
                   - Span represents operation
                   - SpanContext propagates trace
                   - Sampler controls collection
            
               SIMPLE EXAMPLE:
                   @Bean
                   public Tracer tracer() {
                       return OpenTelemetryTracer.builder()
                           .setServiceName("order-service")
                           .build();
                   }
                   
                   @FeignClient(name = "user-service")
                   public interface UserServiceClient {
                       @GetMapping("/users/{id}")
                       User getUser(@PathVariable("id") Long id);
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservices architecture:
                   - Each service creates spans
                   - Trace context propagated via HTTP headers
                   - Jaeger collects traces
                   - Service map shows dependencies
            
               INTERVIEW QUESTION:
                   "How do you implement distributed tracing in Spring Cloud?
                   What is the difference between span and trace?"
            
               COMMON MISTAKES:
                   - Not propagating trace context
                   - Too many spans (noise)
                   - Not sampling appropriately
            
               PERFORMANCE & SCALABILITY:
                   - Tracing adds small overhead
                   - Use sampling for high-volume
                   - Consider async export
            
            ─────────────────────────────────────────────────────────────────────
            
            6. INTER-SERVICE COMMUNICATION
               ─────────────────────────────────────────────────────────────────────
               PATTERNS:
                   - Synchronous: REST/HTTP
                   - Asynchronous: Messaging
                   - Hybrid: Both
            
               CONSIDERATIONS:
                   - Network latency
                   - Failure handling
                   - Data consistency
                   - Security
            
               SIMPLE EXAMPLE:
                   // Synchronous with Feign
                   @Service
                   public class OrderService {
                       private final UserServiceClient userService;
                       
                       public Order createOrder(Long userId, OrderRequest request) {
                           User user = userService.getUser(userId); // Sync call
                           return new Order(user, request);
                       }
                   }
                   
                   // Asynchronous with events
                   @Service
                   public class OrderService {
                       private final ApplicationEventPublisher events;
                       
                       public Order createOrder(OrderRequest request) {
                           Order order = orderRepository.save(new Order(request));
                           events.publishEvent(new OrderCreatedEvent(order)); // Async
                           return order;
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An order processing flow:
                   - Sync: Validate user exists
                   - Async: Send order confirmation email
                   - Sync: Check inventory
                   - Async: Process payment
            
               INTERVIEW QUESTION:
                   "When would you use synchronous vs asynchronous
                   communication between services? What are the trade-offs?"
            
               COMMON MISTAKES:
                   - Chatty communication
                   - Not handling timeouts
                   - Not considering circuit breaker
            
               PERFORMANCE & SCALABILITY:
                   - Async enables better scalability
                   - Consider fan-out patterns
                   - Monitor service dependencies
            
            ─────────────────────────────────────────────────────────────────────
            
            7. SAGA PATTERN
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Saga pattern manages long-running distributed transactions
               through compensating actions.
            
               WHY IT EXISTS:
               - Distributed transaction management
               - Data consistency
               - Fault tolerance
            
               PATTERNS:
                   - Orchestration: Central coordinator
                   - Choreography: Event-driven coordination
            
               SIMPLE EXAMPLE:
                   // Orchestration
                   @Service
                   public class OrderSagaOrchestrator {
                       public void createOrder(OrderRequest request) {
                           try {
                               Order order = orderService.create(request);
                               Payment payment = paymentService.charge(order);
                               inventoryService.reserve(order.getItems());
                           } catch (Exception e) {
                               // Compensate
                               orderService.cancel(order.getId());
                               paymentService.refund(payment.getId());
                               throw e;
                           }
                       }
                   }
                   
                   // Choreography
                   @EventListener
                   public void handle(OrderCreatedEvent event) {
                       paymentService.charge(event.getOrderId());
                   }
                   
                   @EventListener
                   public void handle(PaymentFailedEvent event) {
                       orderService.cancel(event.getOrderId());
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An e-commerce order:
                   - Create order (local)
                   - Charge payment (external)
                   - Reserve inventory (local)
                   - If any fails, compensate previous steps
            
               INTERVIEW QUESTION:
                   "How do you implement the Saga pattern?
                   What is the difference between orchestration and choreography?"
            
               COMMON MISTAKES:
                   - Not handling partial failures
                   - Not making compensating actions idempotent
                   - Not considering eventual consistency
            
               PERFORMANCE & SCALABILITY:
                   - Sagas are eventually consistent
                   - Consider saga timeout
                   - Monitor saga state
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Microservices patterns are essential for:
            - Service-to-service communication
            - Distributed system management
            - Scalability
            - Resilience
            """);
    }
}