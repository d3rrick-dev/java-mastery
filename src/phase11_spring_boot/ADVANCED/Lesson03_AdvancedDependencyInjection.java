package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Advanced Dependency Injection
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. @Primary
 * 2. @Qualifier
 * 3. Lazy initialization
 * 4. Circular dependencies
 * 5. Bean ordering
 * 6. Factory beans
 * 7. Custom bean creation
 */

public class Lesson03_AdvancedDependencyInjection {
    public static void main(String[] args) {
        System.out.println("""
            === ADVANCED DEPENDENCY INJECTION ===
            
            1. @PRIMARY AND @QUALIFIER
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               When multiple beans of the same type exist, @Primary marks the
               default choice, while @Qualifier specifies which bean to inject.
            
               WHY IT EXISTS:
               - Resolves ambiguity in bean selection
               - Enables multiple implementations of same interface
               - Provides explicit control over injection
            
               INTERNAL MECHANICS:
               - PrimaryCandidateComparator sorts candidates
               - QualifierAnnotationAutowireCandidateResolver resolves qualifiers
               - DependencyDescriptor holds injection point metadata
            
               SIMPLE EXAMPLE:
                   @Service
                   @Primary
                   public class PrimaryEmailService implements EmailService { ... }
                   
                   @Service
                   public class SmtpEmailService implements EmailService { ... }
                   
                   @Service
                   public class NotificationService {
                       // Injects PrimaryEmailService by default
                       public NotificationService(EmailService emailService) { ... }
                   }
                   
                   // Explicit selection
                   @Service
                   public class BackupService {
                       public BackupService(@Qualifier("smtpEmailService") EmailService emailService) { ... }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A notification system has multiple SMS providers (Twilio, AWS SNS,
                   local gateway). The primary provider is used by default, but
                   specific services can request a particular provider for
                   redundancy or feature requirements.
            
               INTERVIEW QUESTION:
                   "What happens if you have two @Primary beans of the same type?
                   How does Spring resolve this conflict?"
            
               COMMON MISTAKES:
                   - Multiple @Primary beans (causes startup failure)
                   - Forgetting to use @Qualifier when needed
                   - Using bean names as string literals (refactoring risk)
            
               PERFORMANCE & SCALABILITY:
                   - Qualifier resolution adds minimal overhead
                   - Primary selection is cached at startup
            
               ALTERNATIVES:
                   - @Named (JSR-330 standard)
                   - Custom qualifier annotations
            
            ─────────────────────────────────────────────────────────────────────
            
            2. LAZY INITIALIZATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Lazy initialization delays bean creation until first use,
               reducing startup time and memory footprint.
            
               WHY IT EXISTS:
               - Faster application startup
               - Reduced memory usage for unused beans
               - Avoids circular dependency issues
            
               INTERNAL MECHANICS:
               - BeanFactory uses ObjectProvider for lazy resolution
               - ScopedProxyFactoryBean creates proxy for lazy beans
               - SmartInstantiationAwareBeanPostProcessor handles proxying
            
               SIMPLE EXAMPLE:
                   @Component
                   @Lazy
                   public class HeavyService {
                       @PostConstruct
                       public void init() {
                           // Expensive initialization
                       }
                   }
                   
                   // Or on configuration
                   @Configuration
                   @Lazy
                   public class LazyConfig {
                       @Bean
                       public ExpensiveService expensiveService() { ... }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A reporting service that loads large datasets into memory
                   is marked @Lazy. The application starts quickly, and the
                   service is only initialized when a report is requested.
            
               INTERVIEW QUESTION:
                   "How does @Lazy differ from prototype scope? When would you
                   use each approach?"
            
               COMMON MISTAKES:
                   - Lazy beans still have singleton scope
                   - Not understanding proxy overhead
                   - Lazy initialization can hide startup errors
            
               PERFORMANCE & SCALABILITY:
                   - Reduces startup time significantly
                   - First request has latency penalty
                   - Proxy adds small overhead
            
            ─────────────────────────────────────────────────────────────────────
            
            3. CIRCULAR DEPENDENCIES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               When two beans depend on each other, Spring must handle the
               circular reference during dependency injection.
            
               WHY IT EXISTS:
               - Sometimes unavoidable in complex domains
               - Spring provides mechanisms to resolve
            
               INTERNAL MECHANICS:
               - Three-level cache: singletonObjects, earlySingletonObjects, singletonFactories
               - Early bean reference allows circular injection
               - Constructor injection cannot be resolved (fails fast)
               - Field/Setter injection uses early reference
            
               SIMPLE EXAMPLE:
                   // Constructor injection - FAILS
                   @Service
                   public class ServiceA {
                       public ServiceA(ServiceB serviceB) { ... } // Cannot resolve!
                   }
                   
                   @Service
                   public class ServiceB {
                       public ServiceB(ServiceA serviceA) { ... }
                   }
                   
                   // Field injection - WORKS (with proxy)
                   @Service
                   public class ServiceA {
                       @Autowired
                       private ServiceB serviceB;
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A UserService and CustomerService that reference each other
                   for cross-domain operations. The circular dependency is
                   resolved using field injection, but refactoring to use
                   a shared domain service is better.
            
               INTERVIEW QUESTION:
                   "How does Spring resolve circular dependencies? Why does
                   constructor injection fail for circular references?"
            
               COMMON MISTAKES:
                   - Not recognizing circular dependencies as design smell
                   - Relying on field injection to "fix" design issues
                   - Not understanding early bean references
            
               PERFORMANCE & SCALABILITY:
                   - Circular dependencies add complexity to container
                   - Early bean references use more memory
            
            ─────────────────────────────────────────────────────────────────────
            
            4. FACTORY BEANS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               FactoryBean<T> allows custom bean creation logic, returning
               objects that aren't easily created with standard instantiation.
            
               WHY IT EXISTS:
               - Complex object creation (builders, third-party libraries)
               - Proxy creation
               - Conditional bean creation
            
               INTERNAL MECHANICS:
               - FactoryBeanRegistrySupport manages FactoryBeans
               - getObject() returns the actual bean
               - getObjectType() declares the bean type
               - isSingleton() controls scope
            
               SIMPLE EXAMPLE:
                   @Component
                   public class DataSourceFactoryBean implements FactoryBean<DataSource> {
                       @Override
                       public DataSource getObject() throws Exception {
                           HikariConfig config = new HikariConfig();
                           config.setJdbcUrl(env.getProperty("db.url"));
                           return new HikariDataSource(config);
                       }
                       
                       @Override
                       public Class<?> getObjectType() {
                           return DataSource.class;
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A RedisConnectionFactory that uses a builder pattern
                   to create the connection with custom serializers,
                   connection timeout, and retry configuration.
            
               INTERVIEW QUESTION:
                   "How does FactoryBean differ from @Bean methods?
                   When would you use each approach?"
            
               COMMON MISTAKES:
                   - Not implementing getObjectType() correctly
                   - Forgetting to handle exceptions in getObject()
                   - Not considering singleton vs prototype scope
            
               PERFORMANCE & SCALABILITY:
                   - FactoryBean creation is cached for singleton scope
                   - Consider connection pooling in factory
            
            ─────────────────────────────────────────────────────────────────────
            
            5. BEAN ORDERING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               @Order and Ordered interface control the order of bean
               execution for collections, filters, and interceptors.
            
               WHY IT EXISTS:
               - Predictable execution order
               - Priority-based processing
            
               INTERNAL MECHANICS:
               - AnnotationAwareOrderComparator sorts beans
               - Order annotation uses value (lower = higher priority)
               - Ordered interface provides programmatic control
            
               SIMPLE EXAMPLE:
                   @Component
                   @Order(1)
                   public class SecurityFilter implements Filter { ... }
                   
                   @Component
                   @Order(2)
                   public class LoggingFilter implements Filter { ... }
                   
                   // Or with Ordered interface
                   @Component
                   public class CustomFilter implements Filter, Ordered {
                       @Override
                       public int getOrder() {
                           return 10;
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A request processing pipeline with multiple filters:
                   authentication (order 1), rate limiting (order 2),
                   logging (order 3), security (order 4). Each filter
                   must execute in the correct order.
            
               INTERVIEW QUESTION:
                   "How does Spring determine bean order when multiple
                   ordering mechanisms are used? What's the difference
                   between @Order and Ordered?"
            
               COMMON MISTAKES:
                   - Not using consistent ordering strategy
                   - Magic numbers in @Order
                   - Not considering tie-breakers
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Advanced DI techniques are essential for:
            - Resolving bean ambiguity
            - Optimizing startup performance
            - Handling complex object creation
            - Building maintainable architectures
            """);
    }
}