package phase7;

import java.util.concurrent.atomic.*;

/**
 * LESSON 18: ATOMIC VARIABLES
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Atomic variables are thread-safe variables that support
 * atomic ( indivisible ) operations. They use low-level CPU
 * instructions (CAS - Compare And Swap) to ensure operations
 * complete without interruption.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - synchronized is heavy for simple operations
 * - Need lock-free thread safety
 * - Better performance than synchronized for counters
 * - CAS operations are faster than locking
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Website visitor counter:
 * - Millions of requests per second
 * - synchronized would be a bottleneck
 * - AtomicInteger handles it efficiently
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Rate limiter:
 * - Track requests per second
 * - AtomicLong increments efficiently
 * - No lock contention
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What are atomic variables?"
 * Answer: Lock-free thread-safe variables using CAS,
 *         include AtomicInteger, AtomicLong, etc.
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Using AtomicInteger when you need compound operations
 * - Not understanding CAS retry loops
 * - Thinking atomic = synchronized (different semantics)
 * - Using reference types when primitives suffice
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Atomic operations: ~10-20ns (very fast)
 * - synchronized: ~50-100ns
 * - CAS can spin under high contention
 * - Better for low-contention scenarios
 *
 * ============================================================
 * ATOMIC CLASSES
 * ============================================================
 *
 *   AtomicInteger           AtomicLong
 *   AtomicBoolean           AtomicReference
 *   AtomicIntegerArray      AtomicLongArray
 *   AtomicStampedReference  AtomicMarkableReference
 *   LongAdder               LongAccumulator
 */

public class Lesson18_AtomicVariables {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== ATOMIC VARIABLES ===\n");

        // ============================================================
        // EXAMPLE 1: AtomicInteger basics
        // ============================================================
        System.out.println("--- Example 1: AtomicInteger ---\n");

        AtomicInteger atomicInt = new AtomicInteger(0);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicInt.incrementAndGet();
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                atomicInt.incrementAndGet();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("AtomicInteger count: " + atomicInt.get() + " (expected 2000)\n");

        // ============================================================
        // EXAMPLE 2: Atomic operations
        // ============================================================
        System.out.println("--- Example 2: Atomic Operations ---\n");

        AtomicInteger ops = new AtomicInteger(10);

        System.out.println("Initial: " + ops.get());
        System.out.println("incrementAndGet(): " + ops.incrementAndGet());
        System.out.println("getAndIncrement(): " + ops.getAndIncrement());
        System.out.println("After: " + ops.get());
        System.out.println("addAndGet(5): " + ops.addAndGet(5));
        System.out.println("getAndAdd(3): " + ops.getAndAdd(3));
        System.out.println("After: " + ops.get());
        System.out.println("compareAndSet(17, 20): " + ops.compareAndSet(17, 20));
        System.out.println("After CAS: " + ops.get());
        System.out.println();

        // ============================================================
        // EXAMPLE 3: AtomicLong
        // ============================================================
        System.out.println("--- Example 3: AtomicLong ---\n");

        AtomicLong atomicLong = new AtomicLong(0);

        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    atomicLong.incrementAndGet();
                }
            });
        }

        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("AtomicLong count: " + atomicLong.get() + " (expected 10000)\n");

        // ============================================================
        // EXAMPLE 4: AtomicBoolean
        // ============================================================
        System.out.println("--- Example 4: AtomicBoolean ---\n");

        AtomicBoolean flag = new AtomicBoolean(false);

        Thread setter = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            flag.set(true);
            System.out.println("Setter: flag = true");
        });

        Thread waiter = new Thread(() -> {
            while (!flag.get()) {
                // Busy wait
            }
            System.out.println("Waiter: saw flag = true");
        });

        setter.start();
        waiter.start();
        setter.join();
        waiter.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: AtomicReference
        // ============================================================
        System.out.println("--- Example 5: AtomicReference ---\n");

        AtomicReference<String> atomicRef = new AtomicReference<>("initial");

        Thread t3 = new Thread(() -> {
            atomicRef.compareAndSet("initial", "updated-by-t1");
        });

        Thread t4 = new Thread(() -> {
            atomicRef.compareAndSet("initial", "updated-by-t2");
        });

        t3.start();
        t4.start();
        t3.join();
        t4.join();

        System.out.println("AtomicReference value: " + atomicRef.get());
        System.out.println("(Only one CAS succeeded)\n");

        // ============================================================
        // EXAMPLE 6: AtomicIntegerArray
        // ============================================================
        System.out.println("--- Example 6: AtomicIntegerArray ---\n");

        AtomicIntegerArray atomicArray = new AtomicIntegerArray(5);

        Thread t5 = new Thread(() -> {
            for (int i = 0; i < atomicArray.length(); i++) {
                atomicArray.incrementAndGet(i);
            }
        });

        Thread t6 = new Thread(() -> {
            for (int i = 0; i < atomicArray.length(); i++) {
                atomicArray.incrementAndGet(i);
            }
        });

        t5.start();
        t6.start();
        t5.join();
        t6.join();

        System.out.print("AtomicIntegerArray: [");
        for (int i = 0; i < atomicArray.length(); i++) {
            System.out.print(atomicArray.get(i));
            if (i < atomicArray.length() - 1) System.out.print(", ");
        }
        System.out.println("] (each should be 2)\n");

        // ============================================================
        // EXAMPLE 7: LongAdder (better for high contention)
        // ============================================================
        System.out.println("--- Example 7: LongAdder ---\n");

        LongAdder adder = new LongAdder();

        Thread[] adderThreads = new Thread[10];
        for (int i = 0; i < adderThreads.length; i++) {
            adderThreads[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    adder.increment();
                }
            });
        }

        for (Thread t : adderThreads) t.start();
        for (Thread t : adderThreads) t.join();

        System.out.println("LongAdder count: " + adder.sum() + " (expected 10000)");
        System.out.println("LongAdder is better for high contention!\n");

        // ============================================================
        // EXAMPLE 8: AtomicStampedReference (avoid ABA problem)
        // ============================================================
        System.out.println("--- Example 8: AtomicStampedReference ---\n");

        AtomicStampedReference<String> stampedRef =
            new AtomicStampedReference<>("A", 0);

        int[] stamp = new int[1];
        String value = stampedRef.get(stamp);
        System.out.println("Initial: " + value + ", stamp: " + stamp[0]);

        // CAS with stamp
        boolean success = stampedRef.compareAndSet("A", "B", stamp[0], stamp[0] + 1);
        System.out.println("CAS success: " + success);

        stamp[0] = stampedRef.getStamp();
        System.out.println("New value: " + stampedRef.getReference() + ", stamp: " + stamp[0]);
        System.out.println();

        // ============================================================
        // EXAMPLE 9: When to use atomic variables
        // ============================================================
        System.out.println("--- Example 9: When to Use Atomic Variables ---\n");

        System.out.println("Use Atomic Variables when:");
        System.out.println("1. Simple atomic operations (increment, compare-and-set)");
        System.out.println("2. Low to medium contention");
        System.out.println("3. Need lock-free algorithms");
        System.out.println("4. Counters, flags, references");
        System.out.println();
        System.out.println("Use synchronized/Lock when:");
        System.out.println("1. Compound operations needed");
        System.out.println("2. High contention (CAS spinning)");
        System.out.println("3. Need to lock multiple variables");
        System.out.println("4. Need condition variables (wait/notify)");
    }
}
