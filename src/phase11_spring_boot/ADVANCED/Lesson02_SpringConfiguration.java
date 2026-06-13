package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring Configuration (Advanced)
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Java-based configuration
 * 2. @Configuration
 * 3. @Bean
 * 4. Externalized configuration
 * 5. application.properties
 * 6. application.yml
 * 7. @Value
 * 8. @ConfigurationProperties
 * 9. Configuration validation
 * 10. Profile-specific configuration
 * 11. Property source precedence
 * 12. Secrets management best practices
 */

public class Lesson02_SpringConfiguration {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING CONFIGURATION (ADVANCED) ===
            
            1. JAVA-BASED CONFIGURATION
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Java-based configuration uses @Configuration classes with @Bean
               methods to define beans programmatically instead of XML.
            
               WHY IT EXISTS:
               - Type-safe configuration
               - IDE support and refactoring
               - Compile-time checking
               - No XML parsing overhead
            
               INTERNAL MECHANICS:
               - @Configuration registers as bean definition
               - CGLIB proxies intercept @Bean method calls
               - Prevents multiple bean instances for same method
               - ConfigurationClassPostProcessor processes @Bean methods
            
               SIMPLE EXAMPLE:
                   @Configuration
                   public class DatabaseConfig {
                       @Bean
                       @Primary
                       public DataSource dataSource() {
                           HikariConfig config = new HikariConfig();
                           config.setJdbcUrl("jdbc:postgresql://localhost:5432/mydb");
                           config.setUsername("user");
                           config.setPassword("password");
                           return new HikariDataSource(config);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A multi-tenant application uses @Configuration to create
                   tenant-specific DataSource beans dynamically based on
                   request context. Each tenant gets its own database
                   connection pool with different credentials.
            
               INTERVIEW QUESTION:
                   "How does Spring ensure that @Bean methods are only called
                   once for singleton beans? What role does CGLIB play?"
            
               COMMON MISTAKES:
                   - Calling @Bean method directly (bypasses proxy)
                   - Not using @Configuration on config classes
                   - Bean method calls between @Configuration classes
            
               PERFORMANCE & SCALABILITY:
                   - Configuration classes are processed at startup
                   - Bean creation is cached for singleton scope
                   - Consider lazy initialization for heavy beans
            
               ALTERNATIVES:
                   - XML configuration (legacy)
                   - Groovy configuration
                   - Kotlin DSL configuration
            
            ─────────────────────────────────────────────────────────────────────
            
            2. @VALUE VS @CONFIGURATIONPROPERTIES
               ─────────────────────────────────────────────────────────────────────
               @VALUE:
                   - Injects single property value
                   - SpEL support: @Value("#{systemProperties['app.name']}")
                   - String-based, no type conversion
                   - No validation support
                   
                   @Component
                   public class ServiceConfig {
                       @Value("${app.timeout:5000}")
                       private int timeout;
                   }
            
               @CONFIGURATIONPROPERTIES:
                   - Binds hierarchical properties to object
                   - Type-safe with validation
                   - Relaxed binding (works with different property names)
                   - Metadata support for IDE completion
                   
                   @ConfigurationProperties(prefix = "app.datasource")
                   @Validated
                   public record DataSourceProperties(
                       @NotBlank String url,
                       @NotBlank String username,
                       @NotBlank String password,
                       @Min(1) int maxPoolSize
                   ) {}
            
               INTERNAL MECHANICS:
                   - ConfigurationPropertiesBinder uses PropertySources
                   - ConversionService handles type conversion
                   - Validator validates after binding
            
               INTERVIEW QUESTION:
                   "When would you use @Value over @ConfigurationProperties?
                   What are the security implications of each approach?"
            
               COMMON MISTAKES:
                   - Using @Value for complex configuration
                   - Not enabling @ConfigurationProperties binding
                   - Missing @Validated for validation
            
            ─────────────────────────────────────────────────────────────────────
            
            3. PROPERTY SOURCE PRECEDENCE
               ─────────────────────────────────────────────────────────────────────
               ORDER (HIGHEST TO LOWEST):
                   1. Devtools global settings properties
                   2. Remote config (Spring Cloud Config)
                   3. System properties (System.getProperties())
                   4. OS environment variables
                   5. A packaged application.properties (outside jar)
                   6. A packaged application.yml (outside jar)
                   7. Profile-specific properties (application-{profile}.properties)
                   8. Profile-specific properties (application-{profile}.yml)
                   9. Packaged application-{profile}.properties
                  10. Packaged application-{profile}.yml
                  11. Default properties (SpringApplication.setDefaultProperties)
            
               SIMPLE EXAMPLE:
                   // application.properties
                   app.timeout=5000
                   
                   // Override via command line
                   --app.timeout=10000
                   
                   // Override via environment
                   APP_TIMEOUT=15000
            
               INTERVIEW QUESTION:
                   "How would you override a property in a Docker container?
                   What's the difference between application.properties and
                   application.yml?"
            
               COMMON MISTAKES:
                   - Not understanding precedence order
                   - Hardcoding values in code
                   - Not using profile-specific properties
            
            ─────────────────────────────────────────────────────────────────────
            
            4. SECRETS MANAGEMENT BEST PRACTICES
               ─────────────────────────────────────────────────────────────────────
               APPROACHES:
                   - Environment variables (12-factor app)
                   - Spring Cloud Config with Vault
                   - Kubernetes Secrets
                   - AWS Parameter Store / Secrets Manager
                   - HashiCorp Vault
            
               SPRING BOOT INTEGRATION:
                   application.yml:
                   spring:
                     cloud:
                       vault:
                         uri: https://vault.example.com
                         authentication: AWS_IAM
            
               SIMPLE EXAMPLE:
                   @ConfigurationProperties(prefix = "database")
                   public class DatabaseConfig {
                       private String password; // Injected from Vault
                       
                       // Never log or expose this!
                   }
            
               INTERVIEW QUESTION:
                   "How do you manage database credentials in production?
                   What are the security implications of different approaches?"
            
               COMMON MISTAKES:
                   - Committing secrets to version control
                   - Using default passwords
                   - Not encrypting secrets at rest
                   - Logging sensitive configuration
            
               PERFORMANCE & SCALABILITY:
                   - External secrets add network latency
                   - Cache secrets locally with TTL
                   - Consider connection pooling for secret stores
            
            ─────────────────────────────────────────────────────────────────────
            
            5. CONFIGURATION VALIDATION
               ─────────────────────────────────────────────────────────────────────
               VALIDATION WITH @VALIDATED:
                   @ConfigurationProperties(prefix = "app")
                   @Validated
                   public class AppProperties {
                       @NotBlank
                       private String name;
                       
                       @Min(1)
                       @Max(100)
                       private int maxRetries;
                       
                       @Pattern(regexp = "^[a-z0-9-]+$", message = "Invalid format")
                       private String serviceId;
                   }
            
               CUSTOM VALIDATOR:
                   public class ValidCronExpression implements ConstraintValidator<ValidCron, String> {
                       @Override
                       public boolean isValid(String value, ConstraintValidatorContext ctx) {
                           return value != null && CronSequenceGenerator.isValidExpression(value);
                       }
                   }
            
               INTERVIEW QUESTION:
                   "How do you validate configuration at startup? What happens
                   if validation fails?"
            
               COMMON MISTAKES:
                   - Not validating configuration
                   - Using assertions instead of proper validation
                   - Not handling validation errors gracefully
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring Configuration is the backbone of application flexibility.
            Mastering @Configuration, @Bean, and @ConfigurationProperties is
            essential for building production-ready, maintainable applications.
            """);
    }
}