package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Events
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Event publishing
 * 2. Event listeners
 * 3. Application events
 * 4. Asynchronous events
 * 5. Event-driven design patterns
 */

public class Lesson06_SpringEvents {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING EVENTS ===
            
            1. EVENT PUBLISHING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Spring's event model allows loose coupling between components
               through publish-subscribe pattern.
            
               WHY IT EXISTS:
               - Decouples event producers from consumers
               - Enables reactive programming patterns
               - Supports async processing
            
               INTERNAL MECHANICS:
                   - ApplicationEventMulticaster broadcasts events
                   - SimpleApplicationEventMulticaster uses thread pool
                   - Event listeners are sorted by @Order
                   - Events are published synchronously by default
            
               SIMPLE EXAMPLE:
                   // Custom event
                   public record UserCreatedEvent(Long userId, String email) {}
                   
                   // Publishing
                   @Service
                   public class UserService {
                       @Autowired
                       private ApplicationEventPublisher publisher;
                       
                       public User createUser(User user) {
                           User saved = userRepository.save(user);
                           publisher.publishEvent(new UserCreatedEvent(saved.getId(), saved.getEmail()));
                           return saved;
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An order service publishes OrderCreatedEvent. Multiple listeners:
                   - EmailService sends confirmation
                   - InventoryService reserves items
                   - AnalyticsService records metrics
                   - NotificationService pushes to mobile
            
               INTERVIEW QUESTION:
                   "How does Spring's event system differ from Observer pattern?
                   What is the default multicaster implementation?"
            
               COMMON MISTAKES:
                   - Publishing events in loops (performance impact)
                   - Not handling exceptions in listeners
                   - Blocking listeners in async context
            
               PERFORMANCE & SCALABILITY:
                   - Synchronous events block publisher
                   - Use async for non-critical events
                   - Consider event ordering for consistency
            
            ─────────────────────────────────────────────────────────────────────
            
            2. EVENT LISTENERS
               ─────────────────────────────────────────────────────────────────────
               LISTENER TYPES:
                   - @EventListener: Method-level annotation
                   - implements ApplicationListener<T>: Class-level
                   - GenericApplicationListener: For multiple event types
            
               SIMPLE EXAMPLE:
                   @Component
                   public class UserEventListener {
                       @EventListener
                       public void handleUserCreated(UserCreatedEvent event) {
                           emailService.sendWelcomeEmail(event.email());
                       }
                       
                       @EventListener
                       @Async
                       public void handleUserCreatedAsync(UserCreatedEvent event) {
                           analyticsService.recordUserSignup(event.userId());
                       }
                   }
                   
                   // Or with interface
                   @Component
                   public class UserEventListener implements ApplicationListener<UserCreatedEvent> {
                       @Override
                       public void onApplicationEvent(UserCreatedEvent event) {
                           // Handle event
                       }
                   }
            
               CONDITIONAL LISTENING:
                   @EventListener(condition = "#event.userId > 100")
                   public void handleVipUser(UserCreatedEvent event) {
                       // Only for users with ID > 100
                   }
            
               INTERVIEW QUESTION:
                   "How do you handle exceptions in @EventListener methods?
                   What is the difference between @EventListener and
                   ApplicationListener interface?"
            
               COMMON MISTAKES:
                   - Not handling exceptions (stops other listeners)
                   - Forgetting @Async for async processing
                   - Not using condition for filtering
            
            ─────────────────────────────────────────────────────────────────────
            
            3. APPLICATION EVENTS
               ─────────────────────────────────────────────────────────────────────
               BUILT-IN EVENTS:
                   - ApplicationStartingEvent: Before context refresh
                   - ApplicationEnvironmentPreparedEvent: Environment ready
                   - ApplicationContextInitializedEvent: Context initialized
                   - ApplicationPreparedEvent: Context prepared
                   - ApplicationStartedEvent: Context started
                   - ApplicationReadyEvent: Application ready
                   - ApplicationFailedEvent: Startup failed
            
               SIMPLE EXAMPLE:
                   @Component
                   public class StartupListener {
                       @EventListener
                       public void onReady(ApplicationReadyEvent event) {
                           cacheService.warmUp();
                           log.info("Application fully started");
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A startup listener validates database connectivity,
                   initializes cache, and sends a "service ready"
                   message to a service registry on ApplicationReadyEvent.
            
               INTERVIEW QUESTION:
                   "What is the order of Application lifecycle events?
                   How do you listen for application startup?"
            
               COMMON MISTAKES:
                   - Not handling ApplicationFailedEvent
                   - Heavy operations in early events
            
            ─────────────────────────────────────────────────────────────────────
            
            4. ASYNCHRONOUS EVENTS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               @Async enables non-blocking event processing, improving
               responsiveness and throughput.
            
               WHY IT EXISTS:
               - Non-blocking event processing
               - Better resource utilization
               - Improved user experience
            
               INTERNAL MECHANICS:
                   - AsyncExecutionInterceptor handles async calls
                   - TaskExecutor runs in separate thread
                   - Default: SimpleAsyncTaskExecutor
                   - Recommended: ThreadPoolTaskExecutor
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @EnableAsync
                   public class AsyncConfig {
                       @Bean(name = "eventExecutor")
                       public Executor eventExecutor() {
                           ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
                           executor.setCorePoolSize(5);
                           executor.setMaxPoolSize(10);
                           executor.setQueueCapacity(100);
                           return executor;
                       }
                   }
                   
                   @Component
                   public class AsyncEventListener {
                       @EventListener
                       @Async("eventExecutor")
                       public void handleAsync(UserCreatedEvent event) {
                           // Runs in separate thread
                           notificationService.push(event.userId());
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment service publishes PaymentProcessedEvent.
                   Async listeners handle:
                   - Receipt email (can fail, doesn't block)
                   - Analytics update (eventual consistency)
                   - Loyalty points (can retry)
            
               INTERVIEW QUESTION:
                   "How do you configure the thread pool for @Async events?
                   What happens if the thread pool is exhausted?"
            
               COMMON MISTAKES:
                   - Not enabling @EnableAsync
                   - Using default async executor (no limits)
                   - Not handling async exceptions
            
               PERFORMANCE & SCALABILITY:
                   - Thread pool size affects throughput
                   - Queue size prevents memory exhaustion
                   - Consider rejection policies
            
            ─────────────────────────────────────────────────────────────────────
            
            5. EVENT-DRIVEN DESIGN PATTERNS
               ─────────────────────────────────────────────────────────────────────
               PATTERNS:
                   - Event Sourcing: Store events as source of truth
                   - CQRS: Separate read/write models
                   - Saga: Long-running distributed transactions
                   - Domain Events: Business domain events
            
               SIMPLE EXAMPLE:
                   // Domain event
                   public record OrderPlacedEvent(
                       Long orderId, 
                       String customerId,
                       List<Item> items
                   ) {}
                   
                   // Event handler
                   @Component
                   public class OrderEventHandler {
                       @EventListener
                       @Transactional
                       public void handle(OrderPlacedEvent event) {
                           // Reserve inventory
                           // Charge payment
                           // Send confirmation
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   An e-commerce system uses events for order processing:
                   - OrderPlacedEvent triggers payment processing
                   - PaymentSucceededEvent triggers inventory reservation
                   - InventoryReservedEvent triggers shipping
                   - Each step can be retried independently
            
               INTERVIEW QUESTION:
                   "What is the difference between event-driven and
                   request-driven architectures? How do you ensure
                   event ordering in distributed systems?"
            
               COMMON MISTAKES:
                   - Not handling event duplication
                   - Not making event handlers idempotent
                   - Not considering event ordering
            
               PERFORMANCE & SCALABILITY:
                   - Events enable horizontal scaling
                   - Consider message queues for high volume
                   - Event replay capability for recovery
            
               ALTERNATIVES:
                   - Message queues (RabbitMQ, Kafka)
                   - Reactive streams
                   - Direct method calls (tighter coupling)
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring Events enable:
            - Loose coupling between components
            - Async processing for better performance
            - Event-driven architecture patterns
            - Clean separation of concerns
            """);
    }
}