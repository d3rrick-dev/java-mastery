package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Production Considerations
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson11_ProductionConsiderations {
    public static void main(String[] args) {
        System.out.println("""
                === PRODUCTION CONSIDERATIONS ===

                1. DOCKER BASICS
                   Dockerfile:
                   FROM eclipse-temurin:17-jre-alpine
                   COPY target/*.jar app.jar
                   ENTRYPOINT ["java","-jar","/app.jar"]

                   docker build -t user-service .
                   docker run -p 8080:8080 user-service

                2. SPRING PROFILES
                   - dev: H2 database, debug logging
                   - staging: PostgreSQL, info logging
                   - prod: PostgreSQL, warn logging, caching enabled
                   spring.profiles.active=prod

                3. ACTUATOR (Production Monitoring)
                   management.endpoints.web.exposure.include=health,info,metrics,prometheus
                   - /actuator/health: Health check
                   - /actuator/metrics: JVM, CPU, memory metrics
                   - /actuator/prometheus: Metrics for Prometheus

                4. HEALTH CHECKS
                     @Component
                     public class CustomHealthIndicator implements HealthIndicator {
                         @Override
                         public Health health() {
                             boolean dbHealthy = checkDatabase();
                             if (dbHealthy) {
                                 return Health.up().withDetail("database", "OK").build();
                             }
                             return Health.down().withDetail("database", "DOWN").build();
                         }
                     }

                5. DEPLOYMENT CONCEPTS
                   - Blue-Green: Zero-downtime deployment
                   - Canary: Gradual rollout to subset of users
                   - Rolling: Update instances one by one
                   - Health checks for load balancer
                   - Graceful shutdown: server.shutdown=graceful

                BACKEND REAL-WORLD EXAMPLE:
                   - Docker image built by CI/CD pipeline
                   - Deployed to Kubernetes with 3 replicas
                   - Actuator health checks for liveness/readiness probes
                   - Prometheus scrapes metrics for monitoring
                   - Different configs for dev/staging/prod

                COMMON MISTAKES:
                   - Not externalizing configuration (hardcoded URLs, passwords)
                   - Not implementing health checks
                   - Using same profile for all environments
                   - Not handling graceful shutdown (in-flight requests dropped)
                   - Exposing all actuator endpoints in production
                """);
    }
}
