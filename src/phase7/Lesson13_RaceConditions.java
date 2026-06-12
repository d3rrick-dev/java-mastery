package phase7;

/**
 * LESSON 13: RACE CONDITIONS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * A race condition occurs when multiple threads access shared data
 * and try to change it at the same time. The final result depends on
 * the "race" - which thread gets there first. The outcome is unpredictable.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Threads execute concurrently (interleaved)
 * - Read-modify-write operations are not atomic
 * - No guarantee of execution order
 * - Shared mutable state
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Two people editing same Google Doc:
 * - Person A reads "Hello"
 * - Person B reads "Hello"
 * - A changes to "Hello A"
 * - B changes to "Hello B"
 * - Final: "Hello B" (A's change lost!)
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Inventory system:
 * - Thread 1: Check stock (10 items)
 * - Thread 2: Check stock (10 items)
 * - Thread 1: Sell 1 (stock = 9)
 * - Thread 2: Sell 1 (stock = 9)
 * - Expected: 8, Actual: 9 (oversold!)
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is a race condition?"
 * Answer: When multiple threads access shared data and the result
 *         depends on thread execution order
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Assuming ++ is atomic (it's read-modify-write!)
 * - Not understanding instruction reordering
 * - Thinking local variables are shared
 * - Assuming visibility without synchronization
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Race conditions cause data corruption
 * - Fixing with sync reduces concurrency
 * - Lock-free algorithms are complex but faster
 * - Atomic variables provide middle ground
 *
 * ============================================================
 * RACE CONDITION DIAGRAM
 * ============================================================
 *
 *   Thread A                    Thread B
 *   +------------------------+   +------------------------+
 *   | Read count (0)         |   | Read count (0)         |
 *   | count = count + 1      |   | count = count + 1      |
 *   | Write count (1)        |   | Write count (1)        |
 *   +------------------------+   +------------------------+
 *              |                          |
 *              v                          v
 *         Expected: 2              Actual: 1
 *         (Both incremented)       (One increment lost!)
 */

public class Lesson13_RaceConditions {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== RACE CONDITIONS ===\n");

        // ============================================================
        // EXAMPLE 1: Classic race condition
        // ============================================================
        System.out.println("--- Example 1: Classic Race Condition ---\n");

        RaceConditionCounter counter = new RaceConditionCounter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Count: " + counter.getCount());
        System.out.println("Expected: 2000");
        System.out.println("Lost increments: " + (2000 - counter.getCount()) + "\n");

        // ============================================================
        // EXAMPLE 2: Check-then-act race condition
        // ============================================================
        System.out.println("--- Example 2: Check-Then-Act Race ---\n");

        LazyInit lazy = new LazyInit();

        // Multiple threads check if initialized
        Thread t3 = new Thread(() -> {
            if (!lazy.isInitialized()) {
                lazy.initialize();
            }
        });

        Thread t4 = new Thread(() -> {
            if (!lazy.isInitialized()) {
                lazy.initialize();
            }
        });

        t3.start();
        t4.start();
        t3.join();
        t4.join();

        System.out.println("Init count: " + lazy.getInitCount());
        System.out.println("Expected: 1, Actual may be 2 (race condition!)\n");

        // ============================================================
        // EXAMPLE 3: Read-modify-write race
        // ============================================================
        System.out.println("--- Example 3: Read-Modify-Write ---\n");

        ReadModifyWrite rmw = new ReadModifyWrite();

        Thread t5 = new Thread(() -> {
            for (int i = 0; i < 500; i++) rmw.add(2);
        });

        Thread t6 = new Thread(() -> {
            for (int i = 0; i < 500; i++) rmw.add(3);
        });

        t5.start();
        t6.start();
        t5.join();
        t6.join();

        System.out.println("Final value: " + rmw.getValue());
        System.out.println("Expected: " + (500 * 2 + 500 * 3) + "\n");

        // ============================================================
        // EXAMPLE 4: Visibility race condition
        // ============================================================
        System.out.println("--- Example 4: Visibility Race ---\n");

        VisibilityIssue visibility = new VisibilityIssue();

        Thread writer = new Thread(() -> {
            visibility.value = 42;
            visibility.ready = true;
            System.out.println("Writer set value=42, ready=true");
        });

        Thread reader = new Thread(() -> {
            while (!visibility.ready) {
                // Busy wait
            }
            System.out.println("Reader saw value: " + visibility.value);
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Fixing with synchronization
        // ============================================================
        System.out.println("--- Example 5: Fixed with Synchronization ---\n");

        FixedCounter fixed = new FixedCounter();

        Thread t7 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) fixed.increment();
        });

        Thread t8 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) fixed.increment();
        });

        t7.start();
        t8.start();
        t7.join();
        t8.join();

        System.out.println("Fixed count: " + fixed.getCount() + " (expected 2000)\n");

        // ============================================================
        // EXAMPLE 6: Fixing with AtomicInteger
        // ============================================================
        System.out.println("--- Example 6: Fixed with AtomicInteger ---\n");

        AtomicCounter atomic = new AtomicCounter();

        Thread t9 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) atomic.increment();
        });

        Thread t10 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) atomic.increment();
        });

        t9.start();
        t10.start();
        t9.join();
        t10.join();

        System.out.println("Atomic count: " + atomic.getCount() + " (expected 2000)\n");

        // ============================================================
        // EXAMPLE 7: Types of race conditions
        // ============================================================
        System.out.println("--- Example 7: Types of Race Conditions ---\n");

        System.out.println("1. Check-Then-Act:");
        System.out.println("   if (!initialized) initialize();");
        System.out.println();
        System.out.println("2. Read-Modify-Write:");
        System.out.println("   count++;  // read, modify, write");
        System.out.println();
        System.out.println("3. Visibility:");
        System.out.println("   Thread A writes, Thread B doesn't see it");
        System.out.println();
        System.out.println("4. Atomicity:");
        System.out.println("   Operation appears atomic but isn't");
    }

    // ============================================================
    // EXAMPLE CLASSES
    // ============================================================

    static class RaceConditionCounter {
        private int count = 0;

        public void increment() {
            count++;  // NOT atomic!
        }

        public int getCount() {
            return count;
        }
    }

    static class LazyInit {
        private boolean initialized = false;
        private int initCount = 0;

        public boolean isInitialized() {
            return initialized;
        }

        public void initialize() {
            initCount++;
            initialized = true;
        }

        public int getInitCount() {
            return initCount;
        }
    }

    static class ReadModifyWrite {
        private int value = 0;

        public void add(int amount) {
            value += amount;  // Read-modify-write!
        }

        public int getValue() {
            return value;
        }
    }

    static class VisibilityIssue {
        public int value = 0;
        public boolean ready = false;
    }

    static class FixedCounter {
        private int count = 0;

        public synchronized void increment() {
            count++;
        }

        public synchronized int getCount() {
            return count;
        }
    }

    static class AtomicCounter {
        private final java.util.concurrent.atomic.AtomicInteger count =
            new java.util.concurrent.atomic.AtomicInteger(0);

        public void increment() {
            count.incrementAndGet();
        }

        public int getCount() {
            return count.get();
        }
    }
}
