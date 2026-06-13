package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Core Fundamentals (Advanced)
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Inversion of Control (IoC)
 * 2. Dependency Injection (constructor, field, setter injection)
 * 3. Bean scopes
 * 4. Bean lifecycle
 * 5. Bean initialization and destruction
 * 6. ApplicationContext vs BeanFactory
 * 7. Spring container internals
 * 8. Component scanning
 * 9. Stereotype annotations
 * 10. Conditional bean creation
 * 11. Profiles
 * 12. Environment abstraction
 */

public class Lesson01_SpringCoreFundamentals {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING CORE FUNDAMENTALS (ADVANCED) ===
            
            1. INVERSION OF CONTROL (IoC)
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               IoC is a design principle where the control of object creation, 
               configuration, and lifecycle is transferred from application code 
               to a container (Spring IoC Container).
            
               WHY IT EXISTS:
               - Decouples components from their dependencies
               - Enables easier testing through dependency substitution
               - Centralizes configuration management
               - Promotes loose coupling and high cohesion
            
               INTERNAL MECHANICS:
               - BeanDefinitionRegistry: Stores bean definitions
               - BeanFactory: Core container that creates beans
               - BeanPostProcessor: Modifies beans before/after initialization
               - Instantiation: Uses reflection or factory methods
            
               SIMPLE EXAMPLE:
                   // Without IoC - tight coupling
                   public class OrderService {
                       private final PaymentService paymentService = new StripePaymentService();
                   }
                   
                   // With IoC - loose coupling
                   @Service
                   public class OrderService {
                       private final PaymentService paymentService;
                       
                       public OrderService(PaymentService paymentService) {
                           this.paymentService = paymentService;
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   In a payment processing system, the PaymentProcessor interface
                   can have multiple implementations (Stripe, PayPal, Square).
                   The container injects the appropriate implementation based on
                   configuration, allowing seamless switching between providers
                   without code changes.
            
               INTERVIEW QUESTION:
                   "Explain how IoC promotes loose coupling and makes testing easier.
                   How does it differ from the Service Locator pattern?"
            
               COMMON MISTAKES:
                   - Using static methods for dependencies (can't be mocked)
                   - Not understanding that IoC is about "who calls whom"
                   - Over-engineering with unnecessary abstractions
            
               PERFORMANCE & SCALABILITY:
                   - Singleton beans are thread-safe by design
                   - Prototype beans have higher overhead (new instance each time)
                   - Consider lazy initialization for heavy beans
            
               ALTERNATIVES:
                   - Dagger (compile-time DI)
                   - Guice (Google's DI framework)
                   - Manual factory patterns
            
            ─────────────────────────────────────────────────────────────────────
            
            2. DEPENDENCY INJECTION TYPES
               ─────────────────────────────────────────────────────────────────────
               CONSTRUCTOR INJECTION (RECOMMENDED):
                   - Dependencies are final and immutable
                   - Required dependencies are explicit
                   - Easy to test (no Spring context needed)
                   - Circular dependencies harder to create
                   
                   @Service
                   public class UserService {
                       private final UserRepository userRepository;
                       private final EmailService emailService;
                       
                       public UserService(UserRepository userRepository, 
                                         EmailService emailService) {
                           this.userRepository = userRepository;
                           this.emailService = emailService;
                       }
                   }
            
               SETTER INJECTION:
                   - Optional dependencies
                   - Can be changed at runtime
                   - Harder to test (dependencies can be null)
                   
                   @Service
                   public class UserService {
                       private UserRepository userRepository;
                       
                       @Autowired
                       public void setUserRepository(UserRepository userRepository) {
                           this.userRepository = userRepository;
                       }
                   }
            
               FIELD INJECTION (DEPRECATED):
                   - Uses reflection (slower)
                   - Can't be final
                   - Hard to test without Spring
                   - Hidden dependencies
                   
                   @Service
                   public class UserService {
                       @Autowired
                       private UserRepository userRepository; // Avoid this!
                   }
            
               INTERVIEW QUESTION:
                   "Why is constructor injection preferred over field injection?
                   What are the implications for immutability and testing?"
            
               COMMON MISTAKES:
                   - Using field injection for required dependencies
                   - Mixing injection types in same class
                   - Not making injected fields final
            
            ─────────────────────────────────────────────────────────────────────
            
            3. BEAN SCOPES
               ─────────────────────────────────────────────────────────────────────
               SCOPE TYPES:
                   - singleton: One instance per Spring container (default)
                   - prototype: New instance each time requested
                   - request: One instance per HTTP request
                   - session: One instance per HTTP session
                   - application: One instance per ServletContext
                   - websocket: One instance per WebSocket session
            
               INTERNAL MECHANICS:
                   - Singleton: Cached in ConcurrentHashMap, thread-safe
                   - Prototype: Created via clone or new instantiation
                   - Request/Session: ScopedProxyFactoryBean creates proxy
            
               SIMPLE EXAMPLE:
                   @Component
                   @Scope("prototype")
                   public class UserSession {
                       private String sessionId = UUID.randomUUID().toString();
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A shopping cart service uses session scope to maintain
                   user-specific cart state. Each HTTP session gets its own
                   cart instance, automatically cleaned up when session expires.
            
               INTERVIEW QUESTION:
                   "When would you use prototype scope? What are the thread-safety
                   implications of singleton vs prototype beans?"
            
               COMMON MISTAKES:
                   - Using prototype for stateful beans without understanding cleanup
                   - Not considering thread-safety in singleton beans
                   - Injecting prototype into singleton (singleton holds reference)
            
               PERFORMANCE & SCALABILITY:
                   - Singleton: Memory efficient, but must be thread-safe
                   - Prototype: Higher GC pressure, but isolated state
                   - Request/Session: Memory per user, cleanup critical
            
            ─────────────────────────────────────────────────────────────────────
            
            4. BEAN LIFECYCLE
               ─────────────────────────────────────────────────────────────────────
               LIFECYCLE PHASES:
                   1. BeanDefinition loaded from @Component, @Bean, or XML
                   2. Bean instantiated (constructor called)
                   3. Dependencies injected (DI)
                   4. setXXX() methods called (if applicable)
                   5. @PostConstruct methods called
                   6. Bean ready for use
                   7. @PreDestroy methods called
                   8. Bean destroyed
            
               INITIALIZATION & DESTRUCTION:
                   @Component
                   public class CacheService {
                       @PostConstruct
                       public void initialize() {
                           // Load cache from database
                           System.out.println("Cache initialized");
                       }
                       
                       @PreDestroy
                       public void cleanup() {
                           // Flush cache to disk
                           System.out.println("Cache cleaned up");
                       }
                   }
            
               ALTERNATIVES:
                   - InitializingBean/DisposableBean interfaces
                   - @Bean(initMethod = "init", destroyMethod = "cleanup")
            
               INTERVIEW QUESTION:
                   "What is the order of execution for @PostConstruct,
                   InitializingBean, and initMethod? How do you ensure
                   resources are properly cleaned up?"
            
               COMMON MISTAKES:
                   - Not handling exceptions in @PostConstruct
                   - Resource leaks in @PreDestroy
                   - Assuming initialization order (it's not guaranteed)
            
            ─────────────────────────────────────────────────────────────────────
            
            5. APPLICATIONCONTEXT VS BEANFACTORY
               ─────────────────────────────────────────────────────────────────────
               BEANFACTORY:
                   - Basic IoC container
                   - Lazy initialization (beans created on demand)
                   - No AOP support
                   - No message source (i18n)
                   - No application event publication
            
               APPLICATIONCONTEXT:
                   - Extends BeanFactory
                   - Eager initialization (singleton beans pre-created)
                   - AOP support
                   - Message source for i18n
                   - Application events
                   - Generic type conversion
            
               INTERNAL MECHANICS:
                   - BeanFactory: getBean() triggers instantiation
                   - ApplicationContext: refresh() pre-creates singleton beans
                   - ConfigurableListableBeanFactory: Programmatic access
            
               INTERVIEW QUESTION:
                   "When would you use BeanFactory over ApplicationContext?
                   What are the performance implications of eager vs lazy loading?"
            
               COMMON MISTAKES:
                   - Not understanding that ApplicationContext is usually preferred
                   - Assuming lazy loading is always better
            
            ─────────────────────────────────────────────────────────────────────
            
            6. COMPONENT SCANNING & STEREOTYPE ANNOTATIONS
               ─────────────────────────────────────────────────────────────────────
               STEREOTYPE ANNOTATIONS:
                   - @Component: Generic component
                   - @Service: Business logic layer
                   - @Repository: Data access layer
                   - @Controller: Web MVC controller
                   - @RestController: @Controller + @ResponseBody
            
               COMPONENT SCANNING:
                   - @ComponentScan scans packages for annotated classes
                   - Default: package of @SpringBootApplication
                   - Can specify basePackages or @ComponentScan
            
               INTERNAL MECHANICS:
                   - ClassPathBeanDefinitionScanner scans classpath
                   - Uses PathMatchingResourcePatternResolver
                   - Filters based on AnnotationTypeFilter
            
               SIMPLE EXAMPLE:
                   @SpringBootApplication
                   @ComponentScan(basePackages = "com.company.services")
                   public class Application { }
            
               INTERVIEW QUESTION:
                   "How does component scanning work internally? What happens
                   if two components have the same name in different packages?"
            
               COMMON MISTAKES:
                   - Not specifying basePackages (misses components)
                   - Component name conflicts
                   - Scanning too many packages (performance impact)
            
            ─────────────────────────────────────────────────────────────────────
            
            7. CONDITIONAL BEAN CREATION
               ─────────────────────────────────────────────────────────────────────
               CONDITIONAL ANNOTATIONS:
                   - @ConditionalOnProperty: Based on property value
                   - @ConditionalOnClass: Based on class presence
                   - @ConditionalOnMissingBean: If bean doesn't exist
                   - @ConditionalOnBean: If bean exists
                   - @ConditionalOnMissingClass: If class absent
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @ConditionalOnProperty(name = "cache.enabled", havingValue = "true")
                   public class CacheConfig {
                       @Bean
                       public CacheManager cacheManager() {
                           return new ConcurrentMapCacheManager("users");
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A feature flag system uses @ConditionalOnProperty to enable
                   Redis caching only in production. In development, an in-memory
                   cache is used instead.
            
               INTERVIEW QUESTION:
                   "How does Spring Boot's auto-configuration use conditional
                   annotations? What is the order of condition evaluation?"
            
               COMMON MISTAKES:
                   - Conditions not evaluated in expected order
                   - Missing dependencies causing unexpected behavior
            
            ─────────────────────────────────────────────────────────────────────
            
            8. PROFILES
               ─────────────────────────────────────────────────────────────────────
               PROFILE USAGE:
                   - @Profile("dev"), @Profile("prod")
                   - application-dev.yml, application-prod.yml
                   - spring.profiles.active=dev
            
               INTERNAL MECHANICS:
                   - Environment interface holds active profiles
                   - Conditional bean registration based on profile
                   - Property resolution considers profile-specific files
            
               SIMPLE EXAMPLE:
                   @Configuration
                   @Profile("dev")
                   public class DevDataSourceConfig {
                       @Bean
                       public DataSource dataSource() {
                           return new EmbeddedDatabaseBuilder().setType(H2).build();
                       }
                   }
            
               INTERVIEW QUESTION:
                   "How do profiles work with property resolution? What happens
                   when a bean is defined in multiple profiles?"
            
               COMMON MISTAKES:
                   - Forgetting to set active profile
                   - Profile-specific beans not overriding correctly
                   - Using profiles for feature flags (use feature flags instead)
            
            ─────────────────────────────────────────────────────────────────────
            
            9. ENVIRONMENT ABSTRACTION
               ─────────────────────────────────────────────────────────────────────
               ENVIRONMENT INTERFACE:
                   - Provides access to properties and profiles
                   - Property resolution with precedence
                   - Active and default profiles
            
               PROPERTY RESOLUTION ORDER:
                   1. Command line arguments
                   2. System properties
                   3. OS environment variables
                   4. application-{profile}.properties
                   5. application.properties
                   6. Default properties
            
               SIMPLE EXAMPLE:
                   @Component
                   public class ConfigService {
                       @Autowired
                       private Environment env;
                       
                       public String getDatabaseUrl() {
                           return env.getProperty("database.url", "default-url");
                       }
                   }
            
               INTERVIEW QUESTION:
                   "Explain the property resolution precedence in Spring Boot.
                   How would you override a property in production?"
            
               COMMON MISTAKES:
                   - Not understanding property precedence
                   - Hardcoding values instead of using Environment
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring Core Fundamentals provide the foundation for all Spring applications.
            Understanding IoC, DI, and bean lifecycle is essential for:
            - Writing testable, maintainable code
            - Debugging bean creation issues
            - Optimizing application startup
            - Designing scalable architectures
            """);
    }
}