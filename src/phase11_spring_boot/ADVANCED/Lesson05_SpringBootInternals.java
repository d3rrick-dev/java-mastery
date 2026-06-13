package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Boot Internals
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Auto-configuration
 * 2. Spring Boot starters
 * 3. Conditional auto-configuration
 * 4. Spring Boot startup process
 * 5. Embedded servlet containers
 * 6. Custom starters
 * 7. Actuator internals
 */

public class Lesson05_SpringBootInternals {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING BOOT INTERNALS ===
            
            1. AUTO-CONFIGURATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Auto-configuration automatically configures beans based on
               classpath dependencies, properties, and other conditions.
            
               WHY IT EXISTS:
               - Convention over configuration
               - Reduces boilerplate setup
               - Sensible defaults
            
               INTERNAL MECHANICS:
               - spring.factories contains AutoConfiguration classes
               - AutoConfigurationImportSelector loads configurations
               - Conditional annotations control bean creation
               - ConfigurationClassPostProcessor processes @Configuration
            
               SIMPLE EXAMPLE:
                   // META-INF/spring.factories
                   org.springframework.boot.autoconfigure.EnableAutoConfiguration=\\
                       com.example.MyAutoConfiguration
                   
                   @Configuration
                   @ConditionalOnClass(DataSource.class)
                   @ConditionalOnProperty(name = "datasource.enabled", havingValue = "true")
                   public class MyAutoConfiguration {
                       @Bean
                       @ConditionalOnMissingBean
                       public DataSource dataSource() {
                           return new EmbeddedDatabaseBuilder().build();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A custom auto-configuration for Redis caching that:
                   - Only activates if Redis is on classpath
                   - Creates connection factory with configured properties
                   - Sets up cache manager with TTL defaults
                   - Can be overridden by explicit @Bean definition
            
               INTERVIEW QUESTION:
                   "How does Spring Boot's auto-configuration work?
                   What is the role of @ConditionalOnMissingBean?"
            
               COMMON MISTAKES:
                   - Not understanding condition evaluation order
                   - Forgetting to use @ConditionalOnMissingBean
                   - Auto-configuration conflicts
            
               PERFORMANCE & SCALABILITY:
                   - Auto-configuration adds startup overhead
                   - Use @SpringBootApplication(exclude = {...}) to disable
                   - Consider lazy initialization for heavy auto-config
            
            ─────────────────────────────────────────────────────────────────────
            
            2. CONDITIONAL AUTO-CONFIGURATION
               ─────────────────────────────────────────────────────────────────────
               CONDITIONAL ANNOTATIONS:
                   - @ConditionalOnClass: Class exists on classpath
                   - @ConditionalOnMissingClass: Class doesn't exist
                   - @ConditionalOnBean: Bean exists
                   - @ConditionalOnMissingBean: Bean doesn't exist
                   - @ConditionalOnProperty: Property has value
                   - @ConditionalOnExpression: SpEL expression true
                   - @ConditionalOnWebApplication: Web context
                   - @ConditionalOnJndi: JNDI location exists
                   - @ConditionalOnResource: Resource exists
                   - @ConditionalOnCloudPlatform: Cloud platform detected
            
               CUSTOM CONDITION:
                   public class DatabaseTypeCondition extends SpringBootCondition {
                       @Override
                       public ConditionOutcome getMatchOutcome(ConditionContext context, 
                                                               AnnotatedTypeMetadata metadata) {
                           String dbType = context.getEnvironment().getProperty("app.db-type");
                           if ("mysql".equalsIgnoreCase(dbType)) {
                               return ConditionOutcome.match("MySQL database configured");
                           }
                           return ConditionOutcome.noMatch("Not MySQL database");
                       }
                   }
                   
                   @Configuration
                   @Conditional(DatabaseTypeCondition.class)
                   public class MySqlConfiguration { ... }
            
               INTERVIEW QUESTION:
                   "How do you create a custom conditional annotation?
                   What's the difference between @Conditional and @ConditionalOnX?"
            
               COMMON MISTAKES:
                   - Conditions not mutually exclusive
                   - Not handling missing dependencies gracefully
            
            ─────────────────────────────────────────────────────────────────────
            
            3. SPRING BOOT STARTUP PROCESS
               ─────────────────────────────────────────────────────────────────────
               STARTUP PHASES:
                   1. SpringApplication.run() called
                   2. ApplicationContext created (AnnotationConfig)
                   3. Environment prepared (properties, profiles)
                   4. ApplicationContext refreshed
                   5. BeanDefinitionRegistry populated
                   6. BeanFactory post-processors run
                   7. Bean factory prepared
                   8. Singleton beans pre-instantiated
                   9. ApplicationReadyEvent published
                  10. Application started
            
               INTERNAL MECHANICS:
                   - SpringApplicationBootstrap creates context
                   - ApplicationContextInitializer customizes context
                   - ApplicationListener handles lifecycle events
                   - BeanPostProcessor modifies beans
            
               SIMPLE EXAMPLE:
                   @SpringBootApplication
                   public class Application {
                       public static void main(String[] args) {
                           SpringApplication app = new SpringApplication(Application.class);
                           app.addInitializers(new CustomInitializer());
                           app.run(args);
                       }
                   }
            
               INTERVIEW QUESTION:
                   "What is the difference between ApplicationContextInitializer
                   and ApplicationRunner? When would you use each?"
            
               COMMON MISTAKES:
                   - Heavy operations in initializers
                   - Not understanding event ordering
            
               PERFORMANCE & SCALABILITY:
                   - Startup time critical for cloud deployments
                   - Use lazy initialization for non-critical beans
                   - Profile-specific auto-configuration
            
            ─────────────────────────────────────────────────────────────────────
            
            4. EMBEDDED SERVLET CONTAINERS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Spring Boot embeds Tomcat, Jetty, or Undertow, eliminating
               the need for external application server deployment.
            
               WHY IT EXISTS:
               - Simplified deployment
               - Self-contained applications
               - Consistent runtime environment
            
               INTERNAL MECHANICS:
                   - TomcatServletWebServerFactory creates Tomcat
                   - ServletWebServerApplicationContext holds container
                   - TomcatStarter converts Spring initializers to Tomcat
                   - Connector configured with server.port
            
               SIMPLE EXAMPLE:
                   @SpringBootApplication
                   public class Application {
                       public static void main(String[] args) {
                           System.setProperty("server.port", "8080");
                           SpringApplication.run(Application.class, args);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A microservice uses embedded Tomcat with custom
                   connection pool settings, SSL configuration, and
                   graceful shutdown hooks for Kubernetes.
            
               INTERVIEW QUESTION:
                   "How do you customize the embedded Tomcat in Spring Boot?
                   What are the advantages over external application servers?"
            
               COMMON MISTAKES:
                   - Not configuring connection pools properly
                   - Ignoring memory limits in containers
                   - Not handling graceful shutdown
            
               PERFORMANCE & SCALABILITY:
                   - Embedded servers have lower overhead
                   - Configure max threads and queue size
                   - Use reactive stack for high concurrency
            
            ─────────────────────────────────────────────────────────────────────
            
            5. CUSTOM STARTERS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Custom starters provide reusable auto-configuration and
               dependencies for specific functionality.
            
               WHY IT EXISTS:
               - Share common configuration across projects
               - Encapsulate complex setup
               - Provide opinionated defaults
            
               STRUCTURE:
                   my-company-starter/
                   ├── spring-boot-starter-my-company/
                   │   ├── src/main/java/
                   │   │   └── com/example/autoconfigure/
                   │   │       ├── MyProperties.java
                   │   │       └── MyAutoConfiguration.java
                   │   └── src/main/resources/
                   │       └── META-INF/spring.factories
                   └── my-company-library/
            
               SIMPLE EXAMPLE:
                   // META-INF/spring.factories
                   org.springframework.boot.autoconfigure.EnableAutoConfiguration=\\
                       com.example.autoconfigure.MyAutoConfiguration
                   
                   @ConfigurationProperties(prefix = "my.feature")
                   public class MyProperties {
                       private boolean enabled = true;
                       private String endpoint;
                   }
                   
                   @Configuration
                   @ConditionalOnClass(MyClient.class)
                   @EnableConfigurationProperties(MyProperties.class)
                   public class MyAutoConfiguration {
                       @Bean
                       @ConditionalOnMissingBean
                       public MyClient myClient(MyProperties properties) {
                           return new MyClient(properties.getEndpoint());
                       }
                   }
            
               INTERVIEW QUESTION:
                   "How do you create a custom Spring Boot starter?
                   What is the difference between a starter and a library?"
            
               COMMON MISTAKES:
                   - Not using @ConditionalOnMissingBean
                   - Missing spring.factories entry
                   - Not providing configuration properties
            
            ─────────────────────────────────────────────────────────────────────
            
            6. ACTUATOR INTERNALS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Spring Boot Actuator provides production-ready endpoints
               for monitoring and management.
            
               WHY IT EXISTS:
               - Health monitoring
               - Metrics collection
                   - Runtime insights
            
               INTERNAL MECHANICS:
                   - EndpointHandlerMapping maps endpoints to handlers
                   - HealthEndpoint exposes health indicators
                   - MetricsEndpoint exposes meter registry
                   - EndpointInfo holds endpoint metadata
            
               SIMPLE EXAMPLE:
                   @Component
                   public class DatabaseHealthIndicator implements HealthIndicator {
                       private final DataSource dataSource;
                       
                       @Override
                       public Health health() {
                           try (Connection conn = dataSource.getConnection()) {
                               if (conn.isValid(1)) {
                                   return Health.up().build();
                               }
                           } catch (SQLException e) {
                               return Health.down().withDetail("error", e.getMessage()).build();
                           }
                           return Health.down().build();
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A custom health indicator checks Redis connectivity,
                   Kafka broker availability, and external API status,
                   providing comprehensive system health for Kubernetes
                   liveness and readiness probes.
            
               INTERVIEW QUESTION:
                   "How do you secure Actuator endpoints?
                   What is the difference between health and info endpoints?"
            
               COMMON MISTAKES:
                   - Exposing sensitive endpoints publicly
                   - Not customizing health indicators
                   - Heavy operations in health checks
            
               PERFORMANCE & SCALABILITY:
                   - Health checks should be fast (< 1 second)
                   - Cache health status for expensive checks
                   - Use circuit breaker for external dependencies
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Understanding Spring Boot internals is crucial for:
            - Debugging startup issues
            - Creating custom auto-configurations
            - Optimizing application performance
            - Building reusable starters
            """);
    }
}