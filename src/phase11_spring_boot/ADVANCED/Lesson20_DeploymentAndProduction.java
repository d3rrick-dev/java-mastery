package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Deployment & Production
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. Docker
 * 2. Docker Compose
 * 3. Kubernetes fundamentals
 * 4. CI/CD concepts
 * 5. Environment management
 * 6. Production troubleshooting
 */

public class Lesson20_DeploymentAndProduction {
    public static void main(String[] args) {
        System.out.println("""
            === DEPLOYMENT & PRODUCTION ===
            
            1. DOCKER
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Docker provides containerization, packaging applications
               with all dependencies for consistent deployment.
            
               WHY IT EXISTS:
               - Consistent environments
               - Isolation
               - Portability
               - Resource efficiency
            
               INTERNAL MECHANICS:
                   - Dockerfile defines image
                   - Multi-stage builds for optimization
                   - Layers for caching
                   - Container runtime (Docker/Podman)
            
               SIMPLE EXAMPLE:
                   # Dockerfile
                   FROM eclipse-temurin:17-jdk-alpine AS build
                   WORKDIR /app
                   COPY . .
                   RUN ./mvnw clean package -DskipTests
                   
                   FROM eclipse-temurin:17-jre-alpine
                   WORKDIR /app
                   COPY --from=build /app/target/*.jar app.jar
                   ENTRYPOINT ["java", "-jar", "app.jar"]
                   
                   # application.properties
                   server.port=8080
                   spring.datasource.url=jdbc:postgresql://db:5432/app
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production-ready Docker image:
                   - Multi-stage build
                   - Non-root user
                   - Health check
                   - JVM tuning
                   - Security scanning
            
               INTERVIEW QUESTION:
                   "How do you optimize Docker images for Java applications?
                   What is the difference between CMD and ENTRYPOINT?"
            
               COMMON MISTAKES:
                   - Large image size
                   - Not using multi-stage builds
                   - Running as root
                   - Not setting memory limits
            
               PERFORMANCE & SCALABILITY:
                   - Use distroless images
                   - JVM container awareness
                   - Consider GraalVM native images
            
            ─────────────────────────────────────────────────────────────────────
            
            2. DOCKER COMPOSE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Docker Compose orchestrates multi-container applications
               for local development and testing.
            
               WHY IT EXISTS:
               - Multi-container setup
               - Environment consistency
               - Easy startup
            
               INTERNAL MECHANICS:
                   - docker-compose.yml defines services
                   - Networks for service communication
                   - Volumes for persistence
                   - Depends_on for startup order
            
               SIMPLE EXAMPLE:
                   # docker-compose.yml
                   version: '3.8'
                   services:
                     app:
                       build: .
                       ports:
                         - "8080:8080"
                       environment:
                         - SPRING_PROFILES_ACTIVE=docker
                       depends_on:
                         - db
                         - redis
                       healthcheck:
                         test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
                         interval: 30s
                         timeout: 10s
                         retries: 3
                       
                     db:
                       image: postgres:15
                       environment:
                         - POSTGRES_DB: app
                         - POSTGRES_USER: app
                         - POSTGRES_PASSWORD: secret
                       volumes:
                         - postgres-data:/var/lib/postgresql/data
                       
                     redis:
                       image: redis:7-alpine
                       ports:
                         - "6379:6379"
                   
                   volumes:
                     postgres-data:
            
               REAL-WORLD BACKEND EXAMPLE:
                   A full development environment:
                   - App service with hot reload
                   - Database with test data
                   - Redis for caching
                   - Kafka for messaging
                   - Monitoring stack
            
               INTERVIEW QUESTION:
                   "How do you handle secrets in Docker Compose?
                   What is the difference between depends_on and healthcheck?"
            
               COMMON MISTAKES:
                   - Not using health checks
                   - Hardcoded credentials
                   - Not persisting data
                   - Not setting resource limits
            
               PERFORMANCE & SCALABILITY:
                   - Use compose for local dev
                   - Consider Kubernetes for production
                   - Monitor container resources
            
            ─────────────────────────────────────────────────────────────────────
            
            3. KUBERNETES FUNDAMENTALS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Kubernetes orchestrates containerized applications,
               managing deployment, scaling, and networking.
            
               WHY IT EXISTS:
               - Container orchestration
               - Auto-scaling
               - Self-healing
               - Service discovery
            
               INTERNAL MECHANICS:
                   - Pod is smallest unit
                   - Deployment manages pods
                   - Service exposes pods
                   - ConfigMap for config
                   - Secret for sensitive data
            
               SIMPLE EXAMPLE:
                   # deployment.yaml
                   apiVersion: apps/v1
                   kind: Deployment
                   metadata:
                     name: user-service
                   spec:
                     replicas: 3
                     selector:
                       matchLabels:
                         app: user-service
                     template:
                       metadata:
                         labels:
                           app: user-service
                       spec:
                         containers:
                         - name: app
                           image: user-service:1.0
                           ports:
                           - containerPort: 8080
                           env:
                           - name: SPRING_PROFILES_ACTIVE
                             value: kubernetes
                           resources:
                             requests:
                               memory: "512Mi"
                               cpu: "250m"
                             limits:
                               memory: "1Gi"
                               cpu: "500m"
                           livenessProbe:
                             httpGet:
                               path: /actuator/health/liveness
                               port: 8080
                             initialDelaySeconds: 60
                           readinessProbe:
                             httpGet:
                               path: /actuator/health/readiness
                               port: 8080
                               initialDelaySeconds: 30
                   
                   # service.yaml
                   apiVersion: v1
                   kind: Service
                   metadata:
                     name: user-service
                   spec:
                     selector:
                       app: user-service
                     ports:
                     - port: 80
                       targetPort: 8080
                     type: ClusterIP
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production deployment:
                   - HorizontalPodAutoscaler for scaling
                   - Ingress for routing
                   - ConfigMap for configuration
                   - Secret for database password
                   - PodDisruptionBudget for availability
            
               INTERVIEW QUESTION:
                   "How do you configure health checks in Kubernetes?
                   What is the difference between liveness and readiness probes?"
            
               COMMON MISTAKES:
                   - Not setting resource limits
                   - Not configuring probes
                   - Not using rolling updates
                   - Not handling graceful shutdown
            
               PERFORMANCE & SCALABILITY:
                   - Use HPA for auto-scaling
                   - Consider pod anti-affinity
                   - Use node pools for different workloads
            
            ─────────────────────────────────────────────────────────────────────
            
            4. CI/CD CONCEPTS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               CI/CD automates building, testing, and deployment,
               ensuring code quality and fast delivery.
            
               WHY IT EXISTS:
               - Automated testing
               - Fast feedback
               - Consistent deployments
               - Rollback capability
            
               INTERNAL MECHANICS:
                   - Pipeline defines stages
                   - Build step compiles code
                   - Test step runs tests
                   - Deploy step deploys to environment
                   - Artifact repository stores builds
            
               SIMPLE EXAMPLE:
                   # .github/workflows/ci.yml
                   name: CI
                   on: [push, pull_request]
                   jobs:
                     build:
                       runs-on: ubuntu-latest
                       steps:
                       - uses: actions/checkout@v3
                       - uses: actions/setup-java@v3
                         with:
                           java-version: '17'
                           distribution: 'temurin'
                       - name: Build
                         run: ./mvnw clean verify
                       - name: Test
                         run: ./mvnw test
                       - name: Build Docker image
                         run: docker build -t app:${{ github.sha }} .
                       - name: Push to registry
                         run: |
                           docker tag app:${{ github.sha }} registry/app:${{ github.sha }}
                           docker push registry/app:${{ github.sha }}
            
               REAL-WORLD BACKEND EXAMPLE:
                   A complete CI/CD pipeline:
                   - Code quality checks (SonarQube)
                   - Security scanning (OWASP)
                   - Integration tests
                   - Staging deployment
                   - Manual approval for production
                   - Automated rollback on failure
            
               INTERVIEW QUESTION:
                   "How do you implement blue-green deployment?
                   What is the difference between CI and CD?"
            
               COMMON MISTAKES:
                   - Not running all tests
                   - Not scanning for vulnerabilities
                   - Not having rollback strategy
                   - Not testing in staging
            
               PERFORMANCE & SCALABILITY:
                   - Parallelize build steps
                   - Cache dependencies
                   - Use incremental builds
            
            ─────────────────────────────────────────────────────────────────────
            
            5. ENVIRONMENT MANAGEMENT
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Environment management handles configuration across
               different deployment environments.
            
               WHY IT EXISTS:
               - Environment-specific config
               - Security (secrets)
               - Consistency
            
               INTERNAL MECHANICS:
                   - Spring profiles for environment
                   - Property sources for config
                   - Vault for secrets
                   - ConfigMap/Secrets in Kubernetes
            
               SIMPLE EXAMPLE:
                   // application.yml
                   spring:
                     application:
                       name: user-service
                     profiles:
                       active: ${SPRING_PROFILES_ACTIVE:dev}
                   
                   # application-dev.yml
                   spring:
                     datasource:
                       url: jdbc:h2:mem:testdb
                       username: sa
                       password:
                   
                   # application-prod.yml
                   spring:
                     datasource:
                       url: ${DATABASE_URL}
                       username: ${DATABASE_USERNAME}
                       password: ${DATABASE_PASSWORD}
                   
                   # Kubernetes Secret
                   apiVersion: v1
                   kind: Secret
                   metadata:
                     name: app-secrets
                   type: Opaque
                   data:
                     database-url: base64-encoded-url
                     database-password: base64-encoded-password
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production environment:
                   - Spring Cloud Config for centralized config
                   - Vault for secrets
                   - Environment variables for overrides
                   - Feature flags for gradual rollout
            
               INTERVIEW QUESTION:
                   "How do you manage secrets in production?
                   What is the difference between @Value and @ConfigurationProperties?"
            
               COMMON MISTAKES:
                   - Hardcoded credentials
                   - Not encrypting secrets
                   - Not rotating secrets
                   - Not validating config
            
               PERFORMANCE & SCALABILITY:
                   - Cache config
                   - Use config server
                   - Monitor config changes
            
            ─────────────────────────────────────────────────────────────────────
            
            6. PRODUCTION TROUBLESHOOTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Production troubleshooting involves diagnosing and
               fixing issues in live systems.
            
               WHY IT EXISTS:
               - System reliability
               - Fast incident response
               - Root cause analysis
            
               TOOLS:
                   - Actuator endpoints
                   - Logs and metrics
                   - Thread dumps
                   - Heap dumps
                   - Profiling tools
            
               SIMPLE EXAMPLE:
                   // Actuator configuration
                   management:
                     endpoints:
                       web:
                         exposure:
                           include: health,info,metrics,prometheus,heapdump,threaddump
                     endpoint:
                       health:
                         show-details: always
                   
                   // Custom health indicator
                   @Component
                   public class DatabaseHealthIndicator implements HealthIndicator {
                       private final DataSource dataSource;
                       
                       @Override
                       public Health health() {
                           try (Connection conn = dataSource.getConnection()) {
                               if (conn.isValid(1)) {
                                   return Health.up().build();
                               }
                               return Health.down().withDetail("error", "Invalid connection").build();
                           } catch (SQLException e) {
                               return Health.down().withException(e).build();
                           }
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production troubleshooting setup:
                   - Centralized logging (ELK/EFK)
                   - Metrics dashboard (Prometheus/Grafana)
                   - Alerting on anomalies
                   - Runbook for common issues
                   - On-call rotation
            
               INTERVIEW QUESTION:
                   "How do you diagnose a memory leak in production?
                   What metrics do you monitor for a Spring Boot app?"
            
               COMMON MISTAKES:
                   - Not having proper logging
                   - Not monitoring key metrics
                   - Not having runbooks
                   - Not testing failure scenarios
            
               PERFORMANCE & SCALABILITY:
                   - Use APM tools
                   - Monitor JVM metrics
                   - Set up alerts
                   - Plan capacity
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Production deployment is essential for:
            - Reliable systems
            - Fast delivery
            - Easy troubleshooting
            - Scalable operations
            """);
    }
}