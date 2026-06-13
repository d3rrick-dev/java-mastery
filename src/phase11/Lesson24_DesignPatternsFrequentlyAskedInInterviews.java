package phase11;

/**
 * LESSON 24: DESIGN PATTERNS FREQUENTLY ASKED IN INTERVIEWS
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Creational patterns
 * 2. Structural patterns
 * 3. Behavioral patterns
 * 4. Interview tips
 * 5. Interview questions
 */

public class Lesson24_DesignPatternsFrequentlyAskedInInterviews {
    public static void main(String[] args) {
        System.out.println("""
            === DESIGN PATTERNS IN INTERVIEWS ===
            
            1. CREATIONAL PATTERNS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Patterns for object creation.
            
               WHY IT EXISTS:
               - Flexible object creation
               - Encapsulate creation logic
            
               SIMPLE EXAMPLE:
                   // Singleton:
                   public class DatabaseConnection {
                       private static final DatabaseConnection INSTANCE = 
                           new DatabaseConnection();
                       public static DatabaseConnection getInstance() {
                           return INSTANCE;
                       }
                   }
                   
                   // Factory:
                   public class PaymentFactory {
                       public Payment create(String type) {
                           return switch (type) {
                               case "credit" -> new CreditCardPayment();
                               case "paypal" -> new PayPalPayment();
                               default -> throw new IllegalArgumentException();
                           };
                       }
                   }
                   
                   // Builder:
                   User user = User.builder()
                       .name("Alice")
                       .email("alice@example.com")
                       .build();
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring configuration:
                   @Bean
                   public DataSource dataSource() {
                       return DataSourceBuilder.create()
                           .url(url)
                           .username(username)
                           .build();
                   }
            
               INTERVIEW QUESTION:
                   "What is the Singleton pattern?
                   How to implement thread-safe Singleton?"
            
               COMMON MISTAKES:
                   - Not understanding double-checked locking
                   - Eager vs lazy initialization
            
            ─────────────────────────────────────────────────────────────────────
            
            2. STRUCTURAL PATTERNS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Patterns for class/object composition.
            
               WHY IT EXISTS:
               - Flexible composition
               - Interface adaptation
            
               SIMPLE EXAMPLE:
                   // Adapter:
                   public class ThirdPartyAdapter implements OurInterface {
                       private final ThirdPartyService service;
                       public void doWork() {
                           service.thirdPartyMethod();
                       }
                   }
                   
                   // Decorator:
                   public class LoggingStream extends InputStream {
                       private final InputStream source;
                       public int read() {
                           log("Reading");
                           return source.read();
                       }
                   }
                   
                   // Facade:
                   public class OrderFacade {
                       public void placeOrder(OrderRequest req) {
                           inventory.check(req);
                           payment.process(req);
                           notification.send(req);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring Data repository:
                   // Facade for data access
                   // Hides JPA/Hibernate complexity
                   public interface UserRepository extends JpaRepository<User, Long> {}
            
               INTERVIEW QUESTION:
                   "What is the difference between Adapter and Decorator?
                   When would you use Facade?"
            
               COMMON MISTAKES:
                   - Not understanding intent
                   - Confusing patterns
            
            ─────────────────────────────────────────────────────────────────────
            
            3. BEHAVIORAL PATTERNS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Patterns for object interaction.
            
               WHY IT EXISTS:
               - Communication patterns
               - Responsibility distribution
            
               SIMPLE EXAMPLE:
                   // Observer:
                   public class EventBus {
                       private final List<EventListener> listeners = new ArrayList<>();
                       public void publish(Event e) {
                           listeners.forEach(l -> l.onEvent(e));
                       }
                   }
                   
                   // Strategy:
                   public class Sorter {
                       private final SortStrategy strategy;
                       public void sort(List<T> list) {
                           strategy.sort(list);
                       }
                   }
                   
                   // Command:
                   public class TransferCommand implements Command {
                       public void execute() {
                           from.debit(amount);
                           to.credit(amount);
                       }
                   }
            
               REAL-WORLD BACKEND EXAMPLE:
                   A Spring event system:
                   @EventListener
                   public void handleOrderCreated(OrderCreatedEvent event) {
                       // Observer pattern
                   }
            
               INTERVIEW QUESTION:
                   "How does the Observer pattern work?
                   Give a real-world example."
            
               COMMON MISTAKES:
                   - Not understanding loose coupling
                   - Not knowing real examples
            
            ─────────────────────────────────────────────────────────────────────
            
            4. INTERVIEW TIPS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Prepare for common design pattern questions.
            
               WHY IT EXISTS:
               - Demonstrate design skills
               - Show experience
            
               SIMPLE EXAMPLE:
                   // Prepare answers for:
                   // - When to use Singleton?
                   // - Thread-safe Singleton implementation?
                   // - Factory vs Abstract Factory?
                   // - Real-world examples?
            
               REAL-WORLD BACKEND EXAMPLE:
                   A real example:
                   "In my last project, I used the Strategy pattern for payment processing.
                   We had multiple payment providers and needed to switch between them
                   based on configuration. Strategy allowed us to encapsulate each
                   provider's logic and switch at runtime."
            
               INTERVIEW QUESTION:
                   "What design patterns have you used?
                   How would you implement them?"
            
               COMMON MISTAKES:
                   - Not having real examples
                   - Not understanding trade-offs
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Design patterns are essential for:
            - System design interviews
            - Code quality
            - Communication
            - Best practices
            """);
    }
}
