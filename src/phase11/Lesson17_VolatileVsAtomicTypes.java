package phase11;

import java.util.concurrent.atomic.*;

/**
 * LESSON 17: VOLATILE VS ATOMIC TYPES
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * volatile: Ensures visibility of changes across threads
 * Atomic types: Provide atomic operations (read-modify-write)
 * Like a bulletin board (volatile) vs a safe (atomic).
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - volatile: Visibility without locking
 * - Atomic: Atomic operations without locking
 * - Both are lighter than synchronized
 */

public class Lesson17_VolatileVsAtomicTypes {

    public static void main(String[] args) {
        System.out.println("=== VOLATILE VS ATOMIC TYPES ===\n");

        // ============================================================
        // EXAMPLE 1: volatile for visibility
        // ============================================================
        System.out.println("--- Example 1: volatile Visibility ---\n");

        System.out.println("volatile ensures:");
        System.out.println("  1. Visibility: Changes visible to all threads");
        System.out.println("  2. Ordering: Prevents reordering");
        System.out.println("  3. NOT atomic: read-modify-write still race");
        System.out.println();
        System.out.println("Use volatile for:");
        System.out.println("  - Flags (boolean running)");
        System.out.println("  - Single-writer, multiple-reader");
        System.out.println("  - No compound operations");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Atomic types
        // ============================================================
        System.out.println("--- Example 2: Atomic Types ---\n");

        AtomicInteger atomicInt = new AtomicInteger(0);
        AtomicLong atomicLong = new AtomicLong(0);
        AtomicReference<String> atomicRef = new AtomicReference<>("initial");

        System.out.println("Atomic types provide:");
        System.out.println("  - Atomic read-modify-write");
        System.out.println("  - getAndIncrement(), compareAndSet(), etc.");
        System.out.println("  - Lock-free (uses CAS)");
        System.out.println();

        // Demonstrate atomic operations
        System.out.println("atomicInt.incrementAndGet(): " + atomicInt.incrementAndGet());
        System.out.println("atomicInt.getAndAdd(5): " + atomicInt.getAndAdd(5));
        System.out.println("atomicInt.compareAndSet(6, 10): " + atomicInt.compareAndSet(6, 10));
        System.out.println("atomicInt.get(): " + atomicInt.get());
        System.out.println();

        // ============================================================
        // EXAMPLE 3: volatile vs AtomicInteger
        // ============================================================
        System.out.println("--- Example 3: volatile vs AtomicInteger ---\n");

        System.out.println("volatile int count:");
        System.out.println("  count++;  // NOT atomic! Race condition");
        System.out.println();
        System.out.println("AtomicInteger count:");
        System.out.println("  count.incrementAndGet();  // Atomic!");
        System.out.println();

        // ============================================================
        // EXAMPLE 4: When to use which
        // ============================================================
        System.out.println("--- Example 4: When to Use Which ---\n");

        System.out.println("Use volatile when:");
        System.out.println("  - Simple flag/state variable");
        System.out.println("  - Single writer, multiple readers");
        System.out.println("  - No compound operations needed");
        System.out.println();
        System.out.println("Use Atomic types when:");
        System.out.println("  - Need atomic read-modify-write");
        System.out.println("  - Multiple writers");
        System.out.println("  - Counters, accumulators");
        System.out.println("  - compareAndSet operations");
        System.out.println();
    }

    // ============================================================
    // VOLATILE VS ATOMIC DETAILS
    // ============================================================
    /*
     * volatile:
     * - Visibility guarantee
     * - Prevents instruction reordering
     * - NOT atomic for compound operations
     * - Lightweight
     *
     * Atomic Types (java.util.concurrent.atomic):
     * - AtomicInteger, AtomicLong, AtomicBoolean
     * - AtomicReference<T>
     * - AtomicStampedReference (avoids ABA problem)
     * - LongAdder (better for high contention)
     *
     * Implementation:
     * - Uses Compare-And-Swap (CAS)
     * - Lock-free
     * - CPU-level instruction
     *
     * CAS Operation:
     * - Compare current value with expected
     * - If equal, set to new value
     * - Return success/failure
     * - Retry on failure (spin loop)
     */
}
