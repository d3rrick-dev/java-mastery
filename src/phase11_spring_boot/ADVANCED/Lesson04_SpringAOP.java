package phase11_spring_boot.ADVANCED;

/**
 * SPRING BOOT LESSON: Spring AOP (Aspect-Oriented Programming)
 *
 * Phase 11: Advanced Spring & Spring Boot
 *
 * This lesson covers:
 * 1. What AOP solves
 * 2. Proxies
 * 3. Join Points
 * 4. Pointcuts
 * 5. Advice types
 * 6. Around advice
 * 7. Before advice
 * 8. After advice
 * 9. Exception handling advice
 * 10. Custom annotations with AOP
 * 11. Logging aspects
 * 12. Auditing aspects
 * 13. Performance monitoring aspects
 * 14. Transaction management using AOP
 */

public class Lesson04_SpringAOP {
    public static void main(String[] args) {
        System.out.println("""
            === SPRING AOP (ASPECT-ORIENTED PROGRAMMING) ===
            
            1. WHAT AOP SOLVES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               AOP addresses cross-cutting concerns - functionality that spans
               multiple modules but doesn't belong in business logic.
            
               WHY IT EXISTS:
               - Separates cross-cutting concerns from business logic
               - Reduces code duplication
               - Centralizes common functionality
               - Improves maintainability
            
               PROBLEMS WITHOUT AOP:
                   public class UserService {
                       public User createUser(User user) {
                           // Logging
                           log.info("Creating user: " + user.getEmail());
                           
                           // Security check
                           if (!SecurityContext.hasRole("ADMIN")) {
                               throw new UnauthorizedException();
                           }
                           
                           // Transaction
                           Transaction.begin();
                           try {
                               userRepository.save(user);
                               Transaction.commit();
                           } catch (Exception e) {
                               Transaction.rollback();
                           }
                       }
                   }
            
               WITH AOP:
                   @Service
                   public class UserService {
                       public User createUser(User user) {
                           userRepository.save(user); // Clean business logic
                       }
                   }
            
               INTERVIEW QUESTION:
                   "What are cross-cutting concerns? How does AOP differ from
                   Decorator pattern? What are the limitations of Spring AOP?"
            
               COMMON MISTAKES:
                   - Using AOP for business logic (wrong abstraction)
                   - Not understanding proxy limitations
                   - Overusing aspects (hard to debug)
            
            ─────────────────────────────────────────────────────────────────────
            
            2. PROXIES
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Spring AOP uses proxies to intercept method calls and apply advice.
            
               WHY IT EXISTS:
               - Transparent method interception
               - No code changes in target classes
               - Runtime weaving
            
               INTERNAL MECHANICS:
               - JDK Dynamic Proxy: For interfaces (implements interface)
               - CGLIB Proxy: For classes (extends class, overrides methods)
               - ProxyFactory creates proxy instances
               - Advised interface holds interceptor chain
            
               SIMPLE EXAMPLE:
                   // JDK Proxy (interface-based)
                   public interface UserService {
                       User findById(Long id);
                   }
                   
                   // CGLIB Proxy (class-based)
                   @Service
                   public class UserServiceImpl {
                       public User findById(Long id) { ... }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A metrics aspect uses CGLIB proxy to intercept all service
                   method calls, recording execution time and success/failure
                   counts to Prometheus without modifying service code.
            
               INTERVIEW QUESTION:
                   "What's the difference between JDK and CGLIB proxies?
                   Why can't Spring AOP proxy final methods?"
            
               COMMON MISTAKES:
                   - Not understanding self-invocation bypasses proxy
                   - Final methods not intercepted
                   - Constructors not proxied
            
               PERFORMANCE & SCALABILITY:
                   - Proxy adds small overhead (method call indirection)
                   - CGLIB slightly faster than JDK proxy
                   - Consider AspectJ for compile-time weaving
            
               ALTERNATIVES:
                   - AspectJ (compile-time or load-time weaving)
                   - Manual decorator pattern
                   - Interceptor pattern
            
            ─────────────────────────────────────────────────────────────────────
            
            3. JOIN POINTS & POINTCUTS
               ─────────────────────────────────────────────────────────────────────
               JOIN POINTS:
                   - Execution of methods
                   - Execution of constructors
                   - Field access
                   - Method calls
                   - Exception handling
            
               POINTCUTS:
                   - Define which join points to intercept
                   - Use AspectJ pointcut expression language
                   - Can be combined with &&, ||, !
            
               SIMPLE EXAMPLE:
                   // Pointcut expression
                   @Pointcut("execution(* com.example.service.*.*(..))")
                   public void serviceLayer() {}
                   
                   @Pointcut("@annotation(org.springframework.transaction.annotation.Transactional)")
                   public void transactionalMethods() {}
                   
                   @Pointcut("serviceLayer() && !transactionalMethods()")
                   public void serviceLayerNonTransactional() {}
            
               REAL-WORLD BACKEND EXAMPLE:
                   A security aspect uses pointcuts to intercept all controller
                   methods except those annotated with @PublicEndpoint, ensuring
                   authentication is enforced consistently.
            
               INTERVIEW QUESTION:
                   "What is a join point? How do pointcuts differ from advice?
                   Write a pointcut for all public methods in a service package."
            
               COMMON MISTAKES:
                   - Overly broad pointcuts (performance impact)
                   - Not testing pointcut expressions
                   - Confusing execution and within pointcuts
            
            ─────────────────────────────────────────────────────────────────────
            
            4. ADVICE TYPES
               ─────────────────────────────────────────────────────────────────────
               ADVICE TYPES:
                   - @Before: Before method execution
                   - @AfterReturning: After successful execution
                   - @AfterThrowing: After exception
                   - @After: After method (finally)
                   - @Around: Wraps method execution
            
               AROUND ADVICE:
                   @Aspect
                   public class LoggingAspect {
                       @Around("@annotation(Loggable)")
                       public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
                           long start = System.currentTimeMillis();
                           try {
                               Object result = joinPoint.proceed();
                               log.info("{} executed in {}ms", 
                                   joinPoint.getSignature(), 
                                   System.currentTimeMillis() - start);
                               return result;
                           } catch (Exception e) {
                               log.error("Exception in {}: {}", 
                                   joinPoint.getSignature(), e.getMessage());
                               throw e;
                           }
                       }
                   }
            
               BEFORE ADVICE:
                   @Before("serviceLayer()")
                   public void logBefore(JoinPoint joinPoint) {
                       log.info("Entering: {}", joinPoint.getSignature());
                   }
            
               AFTER ADVICE:
                   @AfterReturning(pointcut = "serviceLayer()", returning = "result")
                   public void logAfter(JoinPoint joinPoint, Object result) {
                       log.info("Exiting: {} with result: {}", 
                           joinPoint.getSignature(), result);
                   }
            
               EXCEPTION HANDLING ADVICE:
                   @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
                   public void logException(JoinPoint joinPoint, Exception ex) {
                       log.error("Exception in {}: {}", joinPoint.getSignature(), ex.getMessage());
                   }
            
               INTERVIEW QUESTION:
                   "When would you use @Around vs @Before/@After?
                   What happens if you don't call proceed() in @Around?"
            
               COMMON MISTAKES:
                   - Not calling proceed() in @Around
                   - Modifying arguments without understanding side effects
                   - Not handling exceptions in @Around
            
            ─────────────────────────────────────────────────────────────────────
            
            5. LOGGING, AUDITING, AND PERFORMANCE ASPECTS
               ─────────────────────────────────────────────────────────────────────
               LOGGING ASPECT:
                   @Aspect
                   @Component
                   public class LoggingAspect {
                       @Around("@within(org.springframework.stereotype.Service)")
                       public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
                           String methodName = joinPoint.getSignature().toShortString();
                           log.info("Entering {}", methodName);
                           try {
                               Object result = joinPoint.proceed();
                               log.info("Exiting {} with result", methodName);
                               return result;
                           } catch (Exception e) {
                               log.error("Exception in {}: {}", methodName, e.getMessage());
                               throw e;
                           }
                       }
                   }
            
               AUDITING ASPECT:
                   @Aspect
                   @Component
                   public class AuditAspect {
                       @AfterReturning("@annotation(Auditable)")
                       public void audit(JoinPoint joinPoint) {
                           Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                           auditService.log(new AuditRecord(
                               auth.getName(),
                               joinPoint.getSignature().getName(),
                               Instant.now()
                           ));
                       }
                   }
            
               PERFORMANCE MONITORING ASPECT:
                   @Aspect
                   @Component
                   public class PerformanceAspect {
                       private final MeterRegistry meterRegistry;
                       
                       @Around("@annotation(Monitored)")
                       public Object monitor(ProceedingJoinPoint joinPoint) throws Throwable {
                           Timer.Sample sample = Timer.start(meterRegistry);
                           try {
                               return joinPoint.proceed();
                           } finally {
                               sample.stop(Timer.builder("method.duration")
                                   .tag("method", joinPoint.getSignature().getName())
                                   .register(meterRegistry));
                           }
                       }
                   }
            
               INTERVIEW QUESTION:
                   "How would you implement a distributed tracing aspect?
                   What are the performance implications of heavy aspects?"
            
               COMMON MISTAKES:
                   - Heavy logging in hot paths
                   - Not using async logging
                   - Aspects that throw exceptions
            
            ─────────────────────────────────────────────────────────────────────
            
            6. TRANSACTION MANAGEMENT USING AOP
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               @Transactional is implemented as an aspect that manages
               transaction boundaries around method execution.
            
               WHY IT EXISTS:
               - Declarative transaction management
               - No boilerplate transaction code
               - Consistent transaction handling
            
               INTERNAL MECHANICS:
                   - TransactionInterceptor implements MethodInterceptor
                   - TransactionAspectSupport manages transaction state
                   - PlatformTransactionManager handles actual transactions
                   - TransactionInfo holds transaction context
            
               SIMPLE EXAMPLE:
                   @Service
                   public class TransferService {
                       @Transactional
                       public void transfer(String fromAccount, String toAccount, BigDecimal amount) {
                           debit(fromAccount, amount);
                           credit(toAccount, amount);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A payment processing service uses @Transactional with
                   REQUIRES_NEW propagation to ensure each payment is
                   independently committed, even if the parent transaction
                   fails.
            
               INTERVIEW QUESTION:
                   "How does @Transactional work under the hood?
                   Why must @Transactional methods be public?"
                   
                   @Transactional works through Spring AOP proxies. When a transactional method is called, the proxy intercepts the call, 
                   starts a transaction using a PlatformTransactionManager, executes the target method, then commits or rolls back 
                   depending on whether an exception occurs. Transaction management is therefore not inside the method itself but around it through the proxy.
                   Transactional methods are typically public because Spring’s proxy mechanism can only intercept methods it can 
                   override or expose through an interface. Private methods cannot be proxied, and internal self-invocation (this.someTransactionalMethod())
                   bypasses the proxy entirely, so the transaction advice is never applied.
            
               COMMON MISTAKES:
                   - Self-invocation bypasses proxy
                   - Not understanding propagation behavior
                   - Using @Transactional on private methods
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Spring AOP is powerful for:
            - Cross-cutting concerns (logging, security, transactions)
            - Performance monitoring
            - Auditing and compliance
            - Clean separation of concerns
            """);
    }
}