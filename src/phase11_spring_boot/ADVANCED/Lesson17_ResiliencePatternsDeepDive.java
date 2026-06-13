package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Resilience Patterns Deep Dive
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Circuit Breaker
 * 2. Retry
 * 3. Bulkhead
 * 4. Rate Limiting
 * 5. Timeouts
 * 6. Fallback mechanisms
 * 7. Resilience4j
 */

public class Lesson17_ResiliencePatternsDeepDive {
    public static void main(String[] args) {
        System.out.println("""
            === RESILIENCE PATTERNS DEEP DIVE ===
            
            1. CIRCUIT BREAKER
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Circuit breaker prevents cascading failures by stopping
               requests to failing services temporarily.
            
               WHY IT EXISTS:
               - Prevent cascading failures
               - Fail fast
               - Allow recovery time
            
               STATES:
                   - CLOSED: Normal operation, track failures
                   - OPEN: Fail fast, no requests sent
                   - HALF_OPEN: Test with limited requests
            
               INTERNAL MECHANICS:
                   - CircuitBreaker tracks call results
                   - Failure rate triggers state change
                   - Wait duration in OPEN state
                   - Success threshold for HALF_OPEN
            
               SIMPLE EXAMPLE:
                   @Service
                   public class PaymentService {
                       private final CircuitBreaker circuitBreaker;
                       
                       public PaymentService(CircuitBreakerRegistry registry) {
                           this.circuitBreaker = registry.circuitBreaker("payment");
                       }
                       
                       public PaymentResult charge(PaymentRequest request) {
                           return circuitBreaker.executeSupplier(() -> {
                               return externalPaymentClient.charge(request);
                           });
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment service:
                   - Circuit breaker on external payment provider
                   - 5 failures in 10 seconds opens circuit
                   - 30-second wait before half-open
                   - Fallback to alternative provider
            
               INTERVIEW QUESTION:
                   "How does a circuit breaker work?
                   What are the different states and transitions?"
            
               COMMON MISTAKES:
                   - Not configuring thresholds properly
                   - Not handling circuit open state
                   - Not allowing time for recovery
            
               PERFORMANCE & SCALABILITY:
                   - Circuit breaker adds minimal overhead
                   - Consider shared circuit breakers
                   - Monitor circuit state
            
            ─────────────────────────────────────────────────────────────────────
            
            2. RETRY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Retry automatically re-attempts failed operations
               with configurable delay and max attempts.
            
               WHY IT EXISTS:
               - Handle transient failures
               - Improve reliability
               - Prevent data loss
            
               INTERNAL MECHANICS:
                   - RetryRegistry holds retry configurations
                   - RetryConfig defines max attempts, delay
                   - Exponential backoff with jitter
                   - RetryException on exhaustion
            
               SIMPLE EXAMPLE:
                   @Service
                   public class InventoryService {
                       private final Retry retry;
                       
                       public InventoryService(RetryRegistry registry) {
                           this.retry = Retry.ofDefaults("inventory");
                       }
                       
                       public boolean reserveItem(String itemId) {
                           return retry.executeSupplier(() -> {
                               return externalInventoryClient.reserve(itemId);
                           });
                       }
                   }
                   
                   // Or with annotation
                   @Retryable(
                       value = {TimeoutException.class, NetworkException.class},
                       maxAttempts = 3,
                       backoff = @Backoff(delay = 1000, multiplier = 2)
                   )
                   public void callExternalService() { ... }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A notification service:
                   - Retry on SMS gateway timeout
                   - Exponential backoff (1s, 2s, 4s)
                   - Max 3 attempts
                   - Dead letter queue for persistent failures
            
               INTERVIEW QUESTION:
                   "How do you implement retry with exponential backoff?
                   What is the difference between retry and circuit breaker?"
            
               COMMON MISTAKES:
                   - Not limiting retry attempts
                   - Not handling retry exhaustion
                   - Retrying non-transient errors
            
               PERFORMANCE & SCALABILITY:
                   - Retries add latency
                   - Consider async retry
                   - Monitor retry rate
            
            ─────────────────────────────────────────────────────────────────────
            
            3. BULKHEAD
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Bulkhead isolates resources, preventing one failing
               component from consuming all resources.
            
               WHY IT EXISTS:
               - Resource isolation
               - Prevent resource exhaustion
               - Fault isolation
            
               INTERNAL MECHANICS:
                   - BulkheadRegistry holds bulkhead configurations
                   - BulkheadConfig defines limits
                   - ThreadPoolBulkhead: Separate thread pool
                   - SemaphoreBulkhead: Concurrent call limit
                   - BulkheadFullException when limit reached
            
               SIMPLE EXAMPLE:
                   @Service
                   public class PaymentService {
                       private final Bulkhead bulkhead;
                       
                       public PaymentService(BulkheadRegistry registry) {
                           this.bulkhead = registry.bulkhead("payment");
                       }
                       
                       public PaymentResult charge(PaymentRequest request) {
                           return bulkhead.executeSupplier(() -> {
                               return externalPaymentClient.charge(request);
                           });
                       }
                   }
                   
                   // Configuration
                   @Bean
                   public BulkheadRegistry bulkheadRegistry() {
                       BulkheadConfig config = BulkheadConfig.custom()
                           .maxConcurrentCalls(10)
                           .maxWaitDuration(Duration.ofSeconds(5))
                           .build();
                       return BulkheadRegistry.of(config);
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A multi-tenant application:
                   - Each tenant gets separate bulkhead
                   - One tenant can't exhaust all resources
                   - Fair resource allocation
            
               INTERVIEW QUESTION:
                   "How does bulkhead differ from thread pool?
                   When would you use semaphore vs thread pool bulkhead?"
            
               COMMON MISTAKES:
                   - Not configuring limits properly
                   - Not handling bulkhead full
                   - Too many bulkheads (overhead)
            
               PERFORMANCE & SCALABILITY:
                   - Bulkhead adds isolation overhead
                   - Consider resource limits
                   - Monitor bulkhead metrics
            
            ─────────────────────────────────────────────────────────────────────
            
            4. RATE LIMITING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Rate limiting controls request frequency, preventing
               abuse and ensuring fair resource usage.
            
               WHY IT EXISTS:
               - Prevent abuse
               - Ensure fair usage
               - Protect downstream services
            
               INTERNAL MECHANICS:
                   - RateLimiterRegistry holds rate limiters
                   - RateLimiterConfig defines limits
                   - Token bucket algorithm
                   - RequestDeniedException when limit exceeded
            
               SIMPLE EXAMPLE:
                   @Service
                   public class ApiService {
                       private final RateLimiter rateLimiter;
                       
                       public ApiService(RateLimiterRegistry registry) {
                           this.rateLimiter = registry.rateLimiter("api", 
                               RateLimiterConfig.custom()
                                   .limitRefreshPeriod(Duration.ofMinutes(1))
                                   .limitForPeriod(100)
                                   .timeoutDuration(Duration.ofSeconds(1))
                                   .build());
                       }
                       
                       public ApiResponse callExternalApi() {
                           return rateLimiter.executeSupplier(() -> {
                               return externalApiClient.call();
                           });
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A public API:
                   - 1000 requests per minute per API key
                   - 429 response when limit exceeded
                   - Rate limit headers in response
                   - Alerting on rate limit breaches
            
               INTERVIEW QUESTION:
                   "How do you implement rate limiting in a distributed system?
                   What is the difference between rate limiting and bulkhead?"
            
               COMMON MISTAKES:
                   - Not handling rate limit exceeded
                   - Not considering distributed rate limiting
                   - Too restrictive limits
            
               PERFORMANCE & SCALABILITY:
                   - Rate limiting adds overhead
                   - Consider Redis for distributed rate limiting
                   - Monitor rate limit metrics
            
            ─────────────────────────────────────────────────────────────────────
            
            5. TIMEOUTS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Timeouts limit how long to wait for a response,
               preventing indefinite blocking.
            
               WHY IT EXISTS:
               - Prevent resource exhaustion
               - Fail fast
               - Improve user experience
            
               INTERNAL MECHANICS:
                   - TimeLimiterRegistry holds time limiters
                   - TimeLimiterConfig defines timeout
                   - CompletableFuture timeout
                   - TimeoutException on timeout
            
               SIMPLE EXAMPLE:
                   @Service
                   public class ExternalService {
                       private final TimeLimiter timeLimiter;
                       
                       public ExternalService(TimeLimiterRegistry registry) {
                           this.timeLimiter = registry.timeLimiter("external",
                               TimeLimiterConfig.custom()
                                   .timeoutDuration(Duration.ofSeconds(5))
                                   .build());
                       }
                       
                       public Response call() {
                           return timeLimiter.executeFutureSupplier(() -> 
                               CompletableFuture.supplyAsync(() -> 
                                   externalClient.call()));
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment gateway integration:
                   - 10-second timeout for payment processing
                   - Circuit breaker on timeout
                   - Fallback to alternative provider
            
               INTERVIEW QUESTION:
                   "How do you configure timeouts for external API calls?
                   What is the difference between connect and read timeout?"
            
               COMMON MISTAKES:
                   - Not setting timeouts
                   - Too short timeouts
                   - Not handling timeout exceptions
            
               PERFORMANCE & SCALABILITY:
                   - Timeouts free up resources
                   - Consider timeout per operation type
                   - Monitor timeout rate
            
            ─────────────────────────────────────────────────────────────────────
            
            6. FALLBACK MECHANISMS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Fallback provides alternative behavior when primary
               operation fails.
            
               WHY IT EXISTS:
               - Graceful degradation
               - Alternative data sources
               - Default responses
            
               INTERNAL MECHANICS:
                   - Fallback method called on failure
                   - Can be static or dynamic
                   - Exception provides failure context
            
               SIMPLE EXAMPLE:
                   @Service
                   public class ProductService {
                       private final CircuitBreaker circuitBreaker;
                       
                       public Product getProduct(Long id) {
                           return circuitBreaker.executeSupplier(() -> {
                               return externalProductService.getProduct(id);
                           }, throwable -> {
                               // Fallback
                               return cacheService.getProduct(id);
                           });
                       }
                   }
                   
                   // Or with annotation
                   @CircuitBreaker(name = "product", fallbackMethod = "getCachedProduct")
                   public Product getProduct(Long id) {
                       return externalProductService.getProduct(id);
                   }
                   
                   public Product getCachedProduct(Long id, Exception ex) {
                       return cacheService.getProduct(id);
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A recommendation service:
                   - Primary: ML-based recommendations
                   - Fallback: Popular items
                   - Circuit breaker on ML service
                   - Graceful degradation
            
               INTERVIEW QUESTION:
                   "How do you implement fallback in Resilience4j?
                   What is the difference between fallback and retry?"
            
               COMMON MISTAKES:
                   - Not providing meaningful fallback
                   - Fallback also failing
                   - Not testing fallback scenarios
            
               PERFORMANCE & SCALABILITY:
                   - Fallback should be fast
                   - Consider cached fallback
                   - Monitor fallback usage
            
            ─────────────────────────────────────────────────────────────────────
            
            7. RESILIENCE4J
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Resilience4j is a lightweight fault tolerance library,
               designed for Java 8+ functional programming.
            
               WHY IT EXISTS:
               - Lightweight alternative to Hystrix
               - Functional programming style
               - Modular design
            
               MODULES:
                   - resilience4j-circuitbreaker
                   - resilience4j-retry
                   - resilience4j-bulkhead
                   - resilience4j-rate-limiter
                   - resilience4j-time-limiter
                   - resilience4j-cache
                   - resilience4j-spring-boot2
            
               SIMPLE EXAMPLE:
                   @Configuration
                   public class ResilienceConfig {
                       @Bean
                       public CircuitBreakerConfig circuitBreakerConfig() {
                           return CircuitBreakerConfig.custom()
                               .failureRateThreshold(50)
                               .waitDurationInOpenState(Duration.ofSeconds(30))
                               .slidingWindowSize(100)
                               .build();
                       }
                   }
                   
                   @Service
                   public class ResilientService {
                       @CircuitBreaker(name = "backend")
                       @Retry(name = "backend")
                       @RateLimiter(name = "backend")
                       @Bulkhead(name = "backend")
                       @TimeLimiter(name = "backend")
                       public CompletableFuture<String> callExternal() {
                           return CompletableFuture.supplyAsync(() -> 
                               externalService.call());
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A comprehensive resilience setup:
                   - Circuit breaker for service calls
                   - Retry for transient errors
                   - Rate limiter for API calls
                   - Bulkhead for resource isolation
                   - Time limiter for timeouts
                   - Metrics for monitoring
            
               INTERVIEW QUESTION:
                   "How do you combine multiple resilience patterns?
                   What is the order of execution for annotations?"
            
               COMMON MISTAKES:
                   - Not configuring patterns properly
                   - Too many patterns (overhead)
                   - Not monitoring pattern metrics
            
               PERFORMANCE & SCALABILITY:
                   - Each pattern adds overhead
                   - Consider pattern combination
                   - Monitor all metrics
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Resilience patterns are essential for:
            - Building reliable systems
            - Handling failures gracefully
            - Preventing cascading failures
            - Ensuring system stability
            """);
    }
}