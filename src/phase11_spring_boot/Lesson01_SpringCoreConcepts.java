package phase11_spring_boot;

/**
 * SPRING BOOT LESSON: Spring Core Concepts (IoC, DI, Bean Lifecycle)
 *
 * Phase 11: Spring Boot Backend Engineering
 */

public class Lesson01_SpringCoreConcepts {
    public static void main(String[] args) {
        System.out.println("""
                === SPRING CORE CONCEPTS ===

                1. INVERSION OF CONTROL (IoC)
                   - Concept: Objects don't create their dependencies
                   - Instead: Container (Spring) creates and injects them
                   - Why: Loose coupling, easier testing, flexible configuration

                   Without IoC:
                     UserService service = new UserService(); // creates own dependency
                   With IoC:
                     UserService service = context.getBean(UserService.class); // container provides

                2. DEPENDENCY INJECTION (DI)
                   - Concept: Dependencies are 'injected' into objects
                   - Types: Constructor, Setter, Field injection
                   - Why: Decouples classes, promotes testability

                   Constructor Injection (Recommended):
                     @Service
                     public class UserService {
                         private final UserRepository repo;
                         public UserService(UserRepository repo) { // injected
                             this.repo = repo;
                         }
                     }

                3. BEAN LIFECYCLE
                   1. Bean definition loaded
                   2. Bean instantiated (constructor)
                   3. Dependencies injected
                   4. @PostConstruct methods called
                   5. Bean is ready for use
                   6. @PreDestroy methods called
                   7. Bean destroyed

                BACKEND REAL-WORLD EXAMPLE:
                   - OrderService needs PaymentService and InventoryService
                   - Spring injects both at startup
                   - If PaymentService changes, only config changes, not OrderService

                COMMON MISTAKES:
                   - Field injection (harder to test, can't be final)
                   - Circular dependencies (A needs B, B needs A)
                   - Using @Autowired on constructors in Spring 4.3+ (redundant)
                   - Not understanding prototype vs singleton scope
                """);
    }
}
