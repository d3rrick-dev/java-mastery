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

public class Lesson17_DeploymentProduction {
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
                   - Docker daemon manages containers
                   - Images built from layers
                   - Container isolation via namespaces/cgroups
                   - Multi-stage builds for optimization
            
               SIMPLE EXAMPLE:
                   # Dockerfile
                   FROM eclipse-temurin:21-jdk-alpine AS build
                   WORKDIR /app
                   COPY . .
                   RUN ./mvnw clean package -DskipTests
                   
                   FROM eclipse-temurin:21-jre-alpine
                   WORKDIR /app
                   COPY --from=build /app/target/*.jar app.jar
                   ENTRYPOINT ["java", "-jar", "/app/app.jar"]
                   
                   # Build and run
                   docker build -t my-app .
                   docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod my-app
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production Docker setup:
                   - Multi-stage build for small image
                   - Non-root user for security
                   - Health check endpoint
                   - JVM tuning for containers
                   - Proper signal handling
            
               INTERVIEW QUESTION:
                   "How do you optimize Docker images for Java applications?
                   What is the difference between CMD and ENTRYPOINT?"
            
               COMMON MISTAKES:
                   - Large images (use distroless or alpine)
                   - Not handling SIGTERM
                   - Not using multi-stage builds
            
               PERFORMANCE & SCALABILITY:
                   - Consider image layer caching
                   - Use .dockerignore
                   - Monitor container resources
            
            ─────────────────────────────────────────────────────────────────────
            
            2. DOCKER COMPOSE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Docker Compose orchestrates multiple containers,
               defining multi-container applications.
            
               WHY IT EXISTS:
               - Multi-container development
               - Environment consistency
               - Easy setup
            
               INTERNAL MECHANICS:
                   - docker-compose.yml defines services
                   - Networks for service communication
                   - Volumes for persistent data
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
                         - DATABASE_URL=jdbc:postgresql://db:5432/mydb
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
                           POSTGRES_DB: mydb
                           POSTGRES_USER: user
                           POSTGRES_PASSWORD: password
                         volumes:
                           - postgres-data:/var/lib/postgresql/data
                       
                       redis:
                         image: redis:7-alpine
                         ports:
                           - "6379:6379"
                   
                   volumes:
                     postgres-data:
               # Run
               docker-compose up -d
            
               REAL-WORLD BACKEND EXAMPLE:
                   A development environment:
                   - App service with hot reload
                   - Database with test data
                   - Redis for caching
                   - Kafka for messaging
                   - All with proper networking
            
               INTERVIEW QUESTION:
                   "How do you handle database migrations in Docker Compose?
                   What is the difference between depends_on and healthcheck?"
            
               COMMON MISTAKES:
                   - Not using health checks
                   - Not persisting data
                   - Not handling service dependencies
            
               PERFORMANCE & SCALABILITY:
                   - Consider resource limits
                   - Use named volumes
                   - Monitor container health
            
            ─────────────────────────────────────────────────────────────────────
            
            3. KUBERNETES FUNDAMENTALS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Kubernetes orchestrates containerized applications,
               providing scaling, networking, and management.
            
               WHY IT EXISTS:
               - Container orchestration
               - Auto-scaling
               - Self-healing
               - Service discovery
            
               KEY CONCEPTS:
                   - Pod: Smallest deployable unit
                   - Service: Network access to pods
                   - Deployment: Manage pod replicas
                   - ConfigMap: Configuration
                   - Secret: Sensitive data
                   - Ingress: External access
                   - Liveness/Readiness probes
            
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
                             image: user-service:latest
                             ports:
                               - containerPort: 8080
                             env:
                               - name: DATABASE_URL
                                 valueFrom:
                                   secretKeyRef:
                                     name: db-secret
                                     key: url
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
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production deployment:
                   - HorizontalPodAutoscaler for scaling
                   - PodDisruptionBudget for availability
                   - NetworkPolicy for security
                   - Resource limits and requests
                   - Rolling updates
            
               INTERVIEW QUESTION:
                   "How do you configure health checks for Kubernetes?
                   What is the difference between liveness and readiness probes?"
            
               COMMON MISTAKES:
                   - Not setting resource limits
                   - Not handling graceful shutdown
                   - Not configuring probes properly
            
               PERFORMANCE & SCALABILITY:
                   - Consider pod anti-affinity
                   - Use horizontal pod autoscaling
                   - Monitor pod resource usage
            
            ─────────────────────────────────────────────────────────────────────
            
            4. CI/CD CONCEPTS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               CI/CD automates building, testing, and deployment,
               enabling fast and reliable releases.
            
               WHY IT EXISTS:
               - Automated testing
               - Fast feedback
               - Reliable deployments
               - Rollback capability
            
               PIPELINE STAGES:
                   - Build: Compile and package
                   - Test: Run unit/integration tests
                   - Security scan: Check vulnerabilities
                   - Deploy: To environment
                   - Verify: Smoke tests
            
               SIMPLE EXAMPLE:
                   # GitHub Actions
                   name: CI/CD
                   on:
                     push:
                       branches: [main]
                   jobs:
                     build:
                       runs-on: ubuntu-latest
                       steps:
                         - uses: actions/checkout@v3
                         - uses: actions/setup-java@v3
                           with:
                             java-version: '21'
                         - run: ./mvnw clean verify
                         - run: docker build -t app .
                         - run: docker push app
                         - run: kubectl set image deployment/app app=app:$GITHUB_SHA
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production pipeline:
                   - Build on merge to main
                   - Run all tests
                   - Security scan with Snyk
                   - Deploy to staging
                   - Run smoke tests
                   - Manual approval for production
                   - Blue-green deployment
            
               INTERVIEW QUESTION:
                   "How do you implement blue-green deployment?
                   What is the difference between CI and CD?"
            
               COMMON MISTAKES:
                   - Not testing in pipeline
                   - Not handling failures
                   - Not implementing rollback
            
               PERFORMANCE & SCALABILITY:
                   - Parallelize build steps
                   - Cache dependencies
                   - Consider build time
            
            ─────────────────────────────────────────────────────────────────────
            
            5. ENVIRONMENT MANAGEMENT
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Environment management handles configuration across
               different deployment environments.
            
               WHY IT EXISTS:
               - Configuration isolation
               - Security
               - Consistency
            
               STRATEGIES:
                   - Profile-specific properties
                   - External configuration
                   - Secrets management
                   - Environment variables
            
               SIMPLE EXAMPLE:
                   # application-prod.yml
                   spring:
                     datasource:
                       url: ${DATABASE_URL}
                       hikari:
                         maximum-pool-size: 20
                   logging:
                     level:
                       com.example: INFO
                   
                   # Kubernetes Secret
                   apiVersion: v1
                   kind: Secret
                   metadata:
                       name: app-secrets
                   type: Opaque
                   data:
                       database-url: base64-encoded-url
            
               REAL-WORLD BACKEND EXAMPLE:
                   A multi-environment setup:
                   - dev: H2 database, debug logging
                   - staging: PostgreSQL, info logging
                   - prod: PostgreSQL, warn logging, external config
                   - Secrets from Vault/Kubernetes
            
               INTERVIEW QUESTION:
                   "How do you manage secrets in production?
                   What is the difference between config and secrets?"
            
               COMMON MISTAKES:
                   - Hardcoding values
                   - Not encrypting secrets
                   - Not validating config
            
               PERFORMANCE & SCALABILITY:
                   - External config adds latency
                   - Cache configuration
                   - Consider config refresh
            
            ─────────────────────────────────────────────────────────────────────
            
            6. PRODUCTION TROUBLESHOOTING
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Production troubleshooting involves diagnosing and
               fixing issues in live systems.
            
               WHY IT EXISTS:
               - Minimize downtime
               - Root cause analysis
               - Performance optimization
            
               TOOLS:
                   - Actuator endpoints
                   - Log aggregation (ELK, Splunk)
                   - Metrics (Prometheus, Grafana)
                   - Distributed tracing (Jaeger, Zipkin)
                   - Profiling (JFR, YourKit)
            
               SIMPLE EXAMPLE:
                   // Actuator endpoint for troubleshooting
                   @Endpoint(id = "troubleshoot")
                   public class TroubleshootEndpoint {
                       @ReadOperation
                       public Map<String, Object> troubleshoot() {
                           return Map.of(
                               "threadCount", Thread.activeCount(),
                               "heapUsed", Runtime.getRuntime().totalMemory(),
                               "cacheSize", cacheService.size()
                           );
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A production incident:
                   - High CPU: Check thread dumps
                   - High memory: Check heap dump
                   - Slow requests: Check traces
                   - Errors: Check logs
                   - Database: Check connection pool
            
               INTERVIEW QUESTION:
                   "How do you diagnose a memory leak in production?
                   What tools do you use for performance troubleshooting?"
            
               COMMON MISTAKES:
                   - Not having proper monitoring
                   - Not logging enough context
                   - Not having runbooks
            
               PERFORMANCE & SCALABILITY:
                   - Proactive monitoring
                   - Alerting on anomalies
                   - Regular performance reviews
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Deployment and production are critical for:
            - Reliable releases
            - Environment consistency
            - Production stability
            - Incident response
            """);
    }
}