package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Spring Boot Fundamentals
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson02_SpringBootFundamentals {
    public static void main(String[] args) {
        System.out.println("""
                === SPRING BOOT FUNDAMENTALS ===

                1. WHAT IS SPRING BOOT?
                   - Framework for building production-ready Spring applications
                   - Convention over configuration
                   - Minimal setup required

                2. AUTO-CONFIGURATION
                   - Spring Boot automatically configures beans based on classpath
                   - Example: If H2 on classpath -> auto-configure datasource
                   - Uses @ConditionalOnClass, @ConditionalOnProperty
                   - Can be disabled: spring.main.allow-bean-definition-overriding=true

                3. STARTERS
                   - Pre-configured dependency sets
                   - spring-boot-starter-web: REST APIs
                   - spring-boot-starter-data-jpa: Database access
                   - spring-boot-starter-security: Security
                   - spring-boot-starter-test: Testing

                4. APPLICATION PROPERTIES
                   - application.properties or application.yml
                   - Server: server.port=8080
                   - Database: spring.datasource.url=jdbc:h2:mem:testdb
                   - Profiles: spring.profiles.active=prod

                5. SPRING BOOT APPLICATION
                     @SpringBootApplication
                     public class MyApp {
                         public static void main(String[] args) {
                             SpringApplication.run(MyApp.class, args);
                         }
                     }

                BACKEND REAL-WORLD EXAMPLE:
                   - Microservice with JPA, Security, and Actuator
                   - Just add starters, configure properties
                   - Spring Boot handles the rest

                COMMON MISTAKES:
                   - Not understanding auto-configuration (hard to debug)
                   - Using wrong starter (e.g., spring-boot-starter-jdbc instead of data-jpa)
                   - Hardcoding configuration instead of using profiles
                   - Not using @SpringBootApplication (missing @ComponentScan)
                """);
    }
}
