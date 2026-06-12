package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Logging and Configuration Management
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson09_LoggingAndConfigurationManagement {
    public static void main(String[] args) {
        System.out.println("""
                === LOGGING AND CONFIGURATION MANAGEMENT ===

                1. LOGGING WITH SLF4J + LOGBACK
                     import org.slf4j.Logger;
                     import org.slf4j.LoggerFactory;

                     @Service
                     public class UserService {
                         private static final Logger log = LoggerFactory.getLogger(UserService.class);

                         public User findUser(Long id) {
                             log.debug("Finding user with id: {}", id);
                             User user = repository.findById(id).orElse(null);
                             if (user == null) {
                                 log.warn("User not found with id: {}", id);
                             } else {
                                 log.info("User found: {}", user.getName());
                             }
                             return user;
                         }
                     }

                2. LOG LEVELS
                   - TRACE: Most detailed, for debugging
                   - DEBUG: Detailed information for debugging
                   - INFO: General informational messages
                   - WARN: Potentially harmful situations
                   - ERROR: Error events that might still allow app to run

                3. CONFIGURATION MANAGEMENT
                   application.yml:
                   app:
                     name: user-service
                     version: 1.0.0
                   server:
                     port: 8080
                   logging:
                     level:
                       com.example: DEBUG
                       org.springframework: INFO

                4. @CONFIGURATIONPROPERTIES
                     @ConfigurationProperties(prefix = "app")
                     public record AppConfig(String name, String version) {}
                   // Usage: @Value("${app.name}") or @ConfigurationProperties

                5. PROFILES
                   application-dev.yml, application-prod.yml
                   spring.profiles.active=dev
                   @Profile("prod") // Bean only loaded in prod

                BACKEND REAL-WORLD EXAMPLE:
                   - Structured JSON logging for ELK stack
                   - Different log levels per environment
                   - Correlation IDs for tracing requests
                   - Externalized config with Spring Cloud Config

                COMMON MISTAKES:
                   - Logging sensitive data (passwords, tokens)
                   - Using System.out.println instead of logger
                   - Not using parameterized logging (string concatenation)
                   - Hardcoding configuration values
                   - Not using MDC for request tracing
                """);
    }
}
