package phase9;

/**
 * LESSON 15: DEPENDENCY INJECTION CONCEPTS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Dependency Injection (DI) is a pattern where objects receive
 * their dependencies from outside instead of creating them.
 * Like a restaurant - you don't bring your own ingredients,
 * the kitchen provides them.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Loose coupling
 * - Easy testing (mock dependencies)
 * - Easy configuration changes
 * - Follows Dependency Inversion Principle
 */

public class Lesson15_DependencyInjectionConcepts {

    public static void main(String[] args) {
        System.out.println("=== DEPENDENCY INJECTION CONCEPTS ===\n");

        // ============================================================
        // EXAMPLE 1: Without DI (tight coupling)
        // ============================================================
        System.out.println("--- Example 1: Without DI ---\n");

        System.out.println("class OrderService {");
        System.out.println("  private MySQLRepository repo = new MySQLRepository();");
        System.out.println("  // Tightly coupled to MySQL!");
        System.out.println("}");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: With DI (loose coupling)
        // ============================================================
        System.out.println("--- Example 2: With DI ---\n");

        System.out.println("class OrderService {");
        System.out.println("  private Repository repo;  // Injected!");
        System.out.println("}");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Constructor injection
        // ============================================================
        System.out.println("--- Example 3: Constructor Injection ---\n");

        Repository repo = new MySQLRepository();
        OrderService service = new OrderService(repo);
        service.placeOrder("order-123");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: DI types
        // ============================================================
        System.out.println("--- Example 4: DI Types ---\n");

        System.out.println("1. Constructor Injection:");
        System.out.println("   - Dependencies via constructor");
        System.out.println("   - Immutable, required dependencies");
        System.out.println();
        System.out.println("2. Setter Injection:");
        System.out.println("   - Dependencies via setter");
        System.out.println("   - Optional dependencies");
        System.out.println();
        System.out.println("3. Field Injection:");
        System.out.println("   - Dependencies via @Autowired");
        System.out.println("   - Less explicit, harder to test");
        System.out.println();
    }

    // ============================================================
    // DI EXAMPLES
    // ============================================================

    interface Repository {
        void save(String data);
    }

    static class MySQLRepository implements Repository {
        @Override
        public void save(String data) {
            System.out.println("Saved to MySQL: " + data);
        }
    }

    static class PostgresRepository implements Repository {
        @Override
        public void save(String data) {
            System.out.println("Saved to PostgreSQL: " + data);
        }
    }

    static class OrderService {
        private final Repository repo;

        // Constructor injection
        public OrderService(Repository repo) {
            this.repo = repo;
        }

        public void placeOrder(String orderId) {
            repo.save(orderId);
        }
    }
}
