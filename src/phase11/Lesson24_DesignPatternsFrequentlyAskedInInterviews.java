package phase11;

/**
 * LESSON 24: DESIGN PATTERNS FREQUENTLY ASKED IN INTERVIEWS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Design patterns are reusable solutions to common problems.
 * Like recipes for software design.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Proven solutions
 * - Common vocabulary
 * - Best practices
 * - Easier communication
 */

public class Lesson24_DesignPatternsFrequentlyAskedInInterviews {

    public static void main(String[] args) {
        System.out.println("=== DESIGN PATTERNS IN INTERVIEWS ===\n");

        // ============================================================
        // EXAMPLE 1: Creational patterns
        // ============================================================
        System.out.println("--- Example 1: Creational Patterns ---\n");

        System.out.println("1. Singleton:");
        System.out.println("   - One instance per application");
        System.out.println("   - Use: Database connections, logging");
        System.out.println();
        System.out.println("2. Factory:");
        System.out.println("   - Create objects without specifying exact class");
        System.out.println("   - Use: Payment processing, document creation");
        System.out.println();
        System.out.println("3. Builder:");
        System.out.println("   - Construct complex objects step by step");
        System.out.println("   - Use: Query builders, complex objects");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Structural patterns
        // ============================================================
        System.out.println("--- Example 2: Structural Patterns ---\n");

        System.out.println("1. Adapter:");
        System.out.println("   - Convert interface to another");
        System.out.println("   - Use: Legacy integration, third-party libs");
        System.out.println();
        System.out.println("2. Decorator:");
        System.out.println("   - Add behavior without modifying class");
        System.out.println("   - Use: I/O streams, middleware");
        System.out.println();
        System.out.println("3. Facade:");
        System.out.println("   - Simplified interface to complex subsystem");
        System.out.println("   - Use: API gateway, service layer");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Behavioral patterns
        // ============================================================
        System.out.println("--- Example 3: Behavioral Patterns ---\n");

        System.out.println("1. Observer:");
        System.out.println("   - Notify multiple objects of changes");
        System.out.println("   - Use: Event handling, pub/sub");
        System.out.println();
        System.out.println("2. Strategy:");
        System.out.println("   - Interchangeable algorithms");
        System.out.println("   - Use: Sorting, validation, pricing");
        System.out.println();
        System.out.println("3. Command:");
        System.out.println("   - Encapsulate request as object");
        System.out.println("   - Use: Undo/redo, task queues");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Interview tips
        // ============================================================
        System.out.println("--- Example 4: Interview Tips ---\n");

        System.out.println("Common interview questions:");
        System.out.println("  - When would you use Singleton?");
        System.out.println("  - How do you implement thread-safe Singleton?");
        System.out.println("  - Difference between Factory and Abstract Factory?");
        System.out.println("  - When to use Builder pattern?");
        System.out.println("  - How does Observer pattern work?");
        System.out.println("  - Real-world examples of patterns in your code?");
        System.out.println();
    }

    // ============================================================
    // DESIGN PATTERNS SUMMARY
    // ============================================================
    /*
     * Most Frequently Asked Patterns:
     *
     * Creational:
     * - Singleton: One instance
     * - Factory: Create objects
     * - Builder: Complex object construction
     * - Prototype: Clone objects
     *
     * Structural:
     * - Adapter: Interface conversion
     * - Decorator: Add behavior
     * - Facade: Simplified interface
     * - Proxy: Control access
     *
     * Behavioral:
     * - Observer: Event notification
     * - Strategy: Algorithm selection
     * - Command: Encapsulate requests
     * - Template Method: Algorithm skeleton
     *
     * Interview Tips:
     * 1. Know the pattern's intent
     * 2. Know when to use it
     * 3. Know trade-offs
     * 4. Have real examples ready
     * 5. Be able to draw UML
     */
}
