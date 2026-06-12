package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between stack and heap memory?
 *
 * Difficulty: BEGINNER
 *
 * Why Interviewers Ask:
 * - Tests understanding of JVM memory model
 * - Shows knowledge of memory management
 * - Reveals awareness of performance implications
 */

public class Lesson11_WhatIsTheDifferenceBetweenStackAndHeap {

    public static void main(String[] args) {
        System.out.println("=== STACK VS HEAP MEMORY ===\n");

        // ============================================================
        // THE QUESTION
        // ============================================================
        System.out.println("QUESTION: What is the difference between stack and heap memory?\n");

        // ============================================================
        // EXPECTED ANSWER
        // ============================================================
        System.out.println("EXPECTED ANSWER:\n");

        System.out.println("STACK MEMORY:");
        System.out.println("  - Thread-private (each thread has its own)");
        System.out.println("  - Stores primitives and object references");
        System.out.println("  - LIFO (Last In, First Out) order");
        System.out.println("  - Fast allocation/deallocation");
        System.out.println("  - Fixed size per thread (default ~1MB)");
        System.out.println("  - Contains: method frames, local variables");
        System.out.println("  - Error: StackOverflowError");
        System.out.println();

        System.out.println("HEAP MEMORY:");
        System.out.println("  - Shared among all threads");
        System.out.println("  - Stores actual objects");
        System.out.println("  - Dynamic allocation");
        System.out.println("  - Garbage collected");
        System.out.println("  - Configurable size (-Xmx, -Xms)");
        System.out.println("  - Contains: objects, arrays, String pool");
        System.out.println("  - Error: OutOfMemoryError");
        System.out.println();

        // ============================================================
        // DEMONSTRATION
        // ============================================================
        System.out.println("--- Demonstration ---\n");

        int primitive = 42;  // Stored on stack
        String reference = "hello";  // Reference on stack, object on heap

        System.out.println("primitive = 42;  // Stored on stack");
        System.out.println("String reference = \"hello\";  // Reference on stack, object on heap");
        System.out.println();

        // ============================================================
        // VISUAL REPRESENTATION
        // ============================================================
        System.out.println("--- Memory Layout ---\n");

        System.out.println("Stack (Thread 1):");
        System.out.println("  +------------------+");
        System.out.println("  | main() frame     |");
        System.out.println("  |  primitive: 42   |");
        System.out.println("  |  reference: 0x1  |----> Heap");
        System.out.println("  +------------------+");
        System.out.println();
        System.out.println("Heap (Shared):");
        System.out.println("  +------------------+");
        System.out.println("  | 0x1: \"hello\"     |");
        System.out.println("  +------------------+");
        System.out.println();

        // ============================================================
        // COMMON MISTAKES
        // ============================================================
        System.out.println("--- Common Mistakes ---\n");

        System.out.println("1. Thinking objects are on stack:");
        System.out.println("   - Only references are on stack");
        System.out.println("   - Objects are always on heap");
        System.out.println();

        System.out.println("2. Confusing StackOverflowError with OutOfMemoryError:");
        System.out.println("   - StackOverflow: Too deep recursion");
        System.out.println("   - OutOfMemory: Heap exhausted");
        System.out.println();

        System.out.println("3. Not knowing String pool location:");
        System.out.println("   - Java 7+: Part of heap");
        System.out.println("   - Java 6 and earlier: PermGen");
        System.out.println();

        // ============================================================
        // FOLLOW-UP QUESTIONS
        // ============================================================
        System.out.println("--- Follow-up Questions ---\n");

        System.out.println("1. What happens when stack memory is full?");
        System.out.println("2. What is the default stack size?");
        System.out.println("3. Where are static variables stored?");
        System.out.println("4. What is Metaspace?");
        System.out.println("5. How does garbage collection work?");
    }
}
