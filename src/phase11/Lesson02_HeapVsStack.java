package phase11;

/**
 * LESSON 2: HEAP VS STACK
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Stack: Fast, thread-private, stores primitives and references
 * Heap: Slower, shared, stores actual objects
 * Like a desk (stack) vs warehouse (heap).
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Stack: Fast allocation/deallocation, method frames
 * - Heap: Dynamic memory, shared objects, GC managed
 */

public class Lesson02_HeapVsStack {

    public static void main(String[] args) {
        System.out.println("=== HEAP VS STACK ===\n");

        // ============================================================
        // EXAMPLE 1: Stack memory
        // ============================================================
        System.out.println("--- Example 1: Stack Memory ---\n");

        int primitive = 42;  // Stored on stack
        String reference = "hello";  // Reference on stack, object on heap

        System.out.println("Primitive '42' stored on stack");
        System.out.println("Reference 'hello' stored on stack");
        System.out.println("String object stored on heap");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Heap memory
        // ============================================================
        System.out.println("--- Example 2: Heap Memory ---\n");

        Person person = new Person("Alice", 30);  // Object on heap
        System.out.println("Person object on heap: " + person);
        System.out.println("Reference 'person' on stack");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Memory allocation visualization
        // ============================================================
        System.out.println("--- Example 3: Memory Layout ---\n");

        System.out.println("Stack (Thread 1):");
        System.out.println("  +------------------+");
        System.out.println("  | main() frame     |");
        System.out.println("  |  primitive: 42   |");
        System.out.println("  |  reference: 0x1  |----> Heap");
        System.out.println("  |  person: 0x2     |----> Heap");
        System.out.println("  +------------------+");
        System.out.println();
        System.out.println("Heap (Shared):");
        System.out.println("  +------------------+");
        System.out.println("  | 0x1: \"hello\"     |");
        System.out.println("  | 0x2: Person      |");
        System.out.println("  |     name: \"Alice\"|");
        System.out.println("  |     age: 30      |");
        System.out.println("  +------------------+");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Stack overflow
        // ============================================================
        System.out.println("--- Example 4: Stack Overflow ---\n");

        System.out.println("Stack overflow occurs when:");
        System.out.println("  - Too many nested method calls (recursion)");
        System.out.println("  - Each call adds a frame to stack");
        System.out.println("  - Stack size is limited (default ~1MB per thread)");
        System.out.println();
        System.out.println("Example: infinite recursion");
        System.out.println("  void recurse() { recurse(); }  // StackOverflowError");
        System.out.println();
    }

    // ============================================================
    // HEAP VS STACK DETAILS
    // ============================================================
    /*
     * Stack Memory:
     * - Thread-private
     * - LIFO (Last In, First Out)
     * - Fast allocation/deallocation
     * - Contains: primitives, object references, method frames
     * - Size: Fixed per thread (default ~1MB)
     * - Error: StackOverflowError
     *
     * Heap Memory:
     * - Shared among threads
     * - Dynamic allocation
     * - GC managed
     * - Contains: actual objects
     * - Size: Configurable (-Xmx, -Xms)
     * - Error: OutOfMemoryError
     *
     * Allocation:
     * - Primitives: Stack
     * - Objects: Heap (reference on stack)
     * - String literals: String Pool (part of heap)
     */
}
