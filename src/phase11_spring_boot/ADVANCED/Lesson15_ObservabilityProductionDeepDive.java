package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Observability & Production Deep Dive
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Spring Boot Actuator
 * 2. Health checks
 * 3. Metrics
 * 4. Micrometer
 * 5. Distributed tracing
 * 6. Structured logging
 * 7. Correlation IDs
 * 8. Monitoring best practices
 */

public class Lesson15_ObservabilityProductionDeepDive {
    public static void main(String[] args) {
        System.out.println("""
            === OBSERVABILITY & PRODUCTION DEEP DIVE ===
            
            1. SPRING BOOT ACTUATOR
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Actuator provides production-ready endpoints for monitoring
               and managing Spring Boot applications.
            
               WHY IT EXISTS:
               - Operational visibility
               - Health monitoring
               - Runtime insights
            
               INTERNAL MECHANICS:
                   - EndpointHandlerMapping maps endpoints
                   - HealthEndpoint exposes health indicators
                   - MetricsEndpoint exposes meter registry
                   - EndpointInfo holds endpoint metadata
            
               SIMPLE EXAMPLE:
                   // application.yml
                   management:
                     endpoints:
                       web:
                         exposure:
                           include: health,info,metrics,prometheus
                     endpoint:
                       health:
                         show-details: always
                   
                   // Custom health indicator
                   @Component
                   public class RedisHealthIndicator implements HealthIndicator {
                       private final RedisConnectionFactory factory;
                       
                       @Override
                       public Health health() {
                           try (Connection conn = factory.getConnection().getNativeConnection()) {
                               if (conn.ping().equals("PONG")) {
                                   return Health.up().build();
                               }
                           } catch (Exception e) {
                               return Health.down().withDetail("error", e.getMessage()).build();
                           }
                           return Health.down().build();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservice with comprehensive health checks:
                   - Database connectivity
                   - Redis availability
                   - External API status
                   - Disk space
                   - Custom business health checks
            
               INTERVIEW QUESTION:
                   "How do you secure Actuator endpoints?
                   What is the difference between liveness and readiness probes?"
            
               COMMON MISTAKES:
                   - Exposing sensitive endpoints publicly
                   - Heavy operations in health checks
                   - Not customizing health indicators
            
               PERFORMANCE & SCALABILITY:
                   - Health checks should be fast
                   - Cache health status for expensive checks
                   - Use circuit breaker for external dependencies
            
            ─────────────────────────────────────────────────────────────────────
            
            2. METRICS & MICROMETER
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Micrometer provides a vendor-neutral metrics facade,
               supporting Prometheus, Datadog, New Relic, etc.
            
               WHY IT EXISTS:
               - Vendor-neutral metrics
               - Easy integration
               - Rich metric types
            
               METRIC TYPES:
                   - Counter: Monotonic incrementing
                   - Timer: Time measurements
                   - Gauge: Current value
                   - DistributionSummary: Distribution of values
                   - LongTaskTimer: Long-running tasks
                   - FunctionCounter: Function-based counter
                   - FunctionTimer: Function-based timer
            
               INTERNAL MECHANICS:
                   - MeterRegistry holds meters
                   - MeterBinder binds meters to registry
                   - Tags provide dimensional data
                   - MeterFilter transforms meters
            
               SIMPLE EXAMPLE:
                   @Service
                   public class OrderService {
                       private final Counter orderCounter;
                       private final Timer orderTimer;
                       
                       public OrderService(MeterRegistry registry) {
                           this.orderCounter = Counter.builder("orders.created")
                               .tag("status", "success")
                               .register(registry);
                           this.orderTimer = Timer.builder("orders.processing.time")
                               .register(registry);
                       }
                       
                       public Order createOrder(OrderRequest request) {
                           return orderTimer.record(() -> {
                               Order order = processOrder(request);
                               orderCounter.increment();
                               return order;
                           });
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment service:
                   - Counter for successful/failed payments
                   - Timer for payment processing time
                   - Gauge for pending payment queue
                   - Tags for payment provider, currency
            
               INTERVIEW QUESTION:
                   "How do you create custom metrics in Spring Boot?
                   What is the difference between Counter and Gauge?"
            
               COMMON MISTAKES:
                   - Not using tags for dimensional data
                   - High-cardinality tags
                   - Not registering meters properly
            
               PERFORMANCE & SCALABILITY:
                   - Metrics have minimal overhead
                   - Consider sampling for high-volume events
                   - Use bounded tags
            
            ─────────────────────────────────────────────────────────────────────
            
            3. DISTRIBUTED TRACING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Distributed tracing tracks requests across service
               boundaries, providing end-to-end visibility.
            
               WHY IT EXISTS:
               - Debug distributed systems
               - Performance analysis
               - Error tracking
            
               INTERNAL MECHANICS:
                   - Tracer creates spans
                   - Span represents operation
                   - SpanContext propagates trace
                   - Sampler controls trace collection
            
               SIMPLE EXAMPLE:
                   @Bean
                   public Tracer tracer() {
                       return OpenTelemetryTracer.builder()
                           .setServiceName("order-service")
                           .build();
                   }
                   
                   @Service
                   public class OrderService {
                       public Order createOrder(OrderRequest request) {
                           Span span = tracer.spanBuilder("create-order").startSpan();
                           try (Scope scope = span.makeCurrent()) {
                               span.setAttribute("order.id", request.getId());
                               return processOrder(request);
                           } finally {
                               span.end();
                           }
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservices architecture:
                   - Each service creates spans
                   - Trace context propagated via HTTP headers
                   - Jaeger or Zipkin collects traces
                   - Service map shows dependencies
            
               INTERVIEW QUESTION:
                   "How do you implement distributed tracing in Spring Boot?
                   What is the difference between span and trace?"
            
               COMMON MISTAKES:
                   - Not propagating trace context
                   - Too many spans (noise)
                   - Not sampling appropriately
            
               PERFORMANCE & SCALABILITY:
                   - Tracing adds small overhead
                   - Use sampling for high-volume services
                   - Consider async export
            
            ─────────────────────────────────────────────────────────────────────
            
            4. STRUCTURED LOGGING & CORRELATION IDS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Structured logging outputs logs in machine-readable format
               (JSON), enabling log aggregation and analysis.
            
               WHY IT EXISTS:
               - Log aggregation (ELK, Splunk)
               - Searchable logs
               - Correlation across services
            
               INTERNAL MECHANICS:
                   - MDC (Mapped Diagnostic Context) holds context
                   - Log pattern includes MDC fields
                   - Correlation ID generated per request
            
               SIMPLE EXAMPLE:
                   // logback-spring.xml
                   <configuration>
                       <appender name="JSON" class="net.logstash.logback.appender.LoggingEventAsyncDisruptorAppender">
                           <encoder class="net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder">
                               <mdc/>
                               <timestamp/>
                           </encoder>
                       </appender>
                   </configuration>
                   
                   // Correlation ID filter
                   @Component
                   public class CorrelationIdFilter extends OncePerRequestFilter {
                       @Override
                       protected void doFilterInternal(HttpServletRequest request,
                                                     HttpServletResponse response,
                                                     FilterChain filterChain) throws ServletException, IOException {
                           String correlationId = UUID.randomUUID().toString();
                           MDC.put("correlationId", correlationId);
                           try {
                               filterChain.doFilter(request, response);
                           } finally {
                               MDC.clear();
                           }
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A distributed system:
                   - Correlation ID generated at API gateway
                   - Propagated to all services
                   - All logs include correlation ID
                   - Easy to trace request across services
            
               INTERVIEW QUESTION:
                   "How do you implement correlation IDs in a microservices
                   architecture? What is MDC?"
            
               COMMON MISTAKES:
                   - Not cleaning up MDC (memory leak)
                   - Not propagating correlation ID
                   - Logging sensitive data
            
               PERFORMANCE & SCALABILITY:
                   - Async logging for performance
                   - Consider log volume
                   - Use sampling for debug logs
            
            ─────────────────────────────────────────────────────────────────────
            
            5. MONITORING BEST PRACTICES
               ─────────────────────────────────────────────────────────────────────
               BEST PRACTICES:
                   - Use RED metrics (Rate, Errors, Duration)
                   - Monitor JVM metrics
                   - Alert on SLOs, not raw metrics
                   - Use dashboards for visualization
                   - Implement health checks
                   - Log at appropriate levels
            
               METRICS TO MONITOR:
                   - HTTP: request rate, error rate, latency
                   - JVM: heap, GC, threads
                   - Database: connection pool, query time
                   - Cache: hit ratio, eviction rate
                   - Business: orders, payments, users
            
               SIMPLE EXAMPLE:
                   @RestController
                   public class UserController {
                       private final Timer responseTimer;
                       private final Counter errorCounter;
                       
                       @GetMapping("/users/{id}")
                       public User getUser(@PathVariable Long id) {
                           return responseTimer.record(() -> {
                               try {
                                   return userService.findById(id);
                               } catch (Exception e) {
                                   errorCounter.increment();
                                   throw e;
                               }
                           });
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production system:
                   - Prometheus scrapes metrics
                   - Grafana dashboards for visualization
                   - AlertManager for alerts
                   - SLOs: 99% requests < 200ms, 99.9% availability
            
               INTERVIEW QUESTION:
                   "What metrics would you monitor for a production API?
                   How do you set up alerting for SLOs?"
            
               COMMON MISTAKES:
                   - Alert fatigue (too many alerts)
                   - Not monitoring business metrics
                   - Not testing alert conditions
            
               PERFORMANCE & SCALABILITY:
                   - Metrics collection has overhead
                   - Use appropriate sampling
                   - Consider metric cardinality
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Observability is critical for:
            - Production monitoring
            - Debugging distributed systems
            - Performance optimization
            - Incident response
            """);
    }
}