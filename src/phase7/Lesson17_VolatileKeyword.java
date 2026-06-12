package phase7;

/**
 * LESSON 17: VOLATILE KEYWORD
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * volatile is a keyword that ensures visibility of changes to variables
 * across threads. When a variable is volatile, any write to it is
 * immediately visible to all other threads.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - CPU caches can hide changes from other threads
 * - Compiler/JVM can reorder instructions
 * - Need guaranteed visibility without full synchronization
 * - Lightweight alternative to synchronized for simple cases
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Stop flag for a background thread:
 * - Main thread sets stopRequested = true
 * - Worker thread checks stopRequested
 * - Without volatile: worker may never see the change
 * - With volatile: worker sees change immediately
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Configuration reload:
 * - Config thread updates configVersion
 * - Worker threads check configVersion
 * - volatile ensures workers see new version
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What does volatile do?"
 * Answer: Ensures visibility of writes across threads,
 *         prevents instruction reordering
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Thinking volatile makes ++ atomic (it doesn't!)
 * - Using volatile instead of synchronized for compound operations
 * - Thinking volatile is enough for all thread safety
 * - Using volatile with object references (reference is volatile, not object)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - volatile read: ~same as normal read
 * - volatile write: ~2-3x slower than normal write
 * - Prevents some JVM optimizations
 * - Much cheaper than synchronized
 *
 * ============================================================
 * VOLATILE GUARANTEES
 * ============================================================
 *
 *   1. Visibility: Write to volatile is visible to all threads
 *   2. Ordering: No reordering across volatile barrier
 *   3. Atomicity: Single read/write is atomic (not compound ops!)
 *
 *   volatile DOES NOT guarantee:
 *   - Atomicity of ++, -=, etc.
 *   - Mutual exclusion
 *   - Compound operation atomicity
 */

public class Lesson17_VolatileKeyword {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== VOLATILE KEYWORD ===\n");

        // ============================================================
        // EXAMPLE 1: Without volatile (broken)
        // ============================================================
        System.out.println("--- Example 1: Without Volatile (Broken) ---\n");

        NoVolatileRunner runner = new NoVolatileRunner();
        Thread worker = new Thread(runner);
        worker.start();

        Thread.sleep(100);
        runner.setRunning(false);
        System.out.println("Set running = false (without volatile)");
        System.out.println("Worker may never stop!\n");

        worker.join(1000);
        if (worker.isAlive()) {
            System.out.println("Worker still running (visibility issue!)\n");
            worker.interrupt();
        }

        // ============================================================
        // EXAMPLE 2: With volatile (fixed)
        // ============================================================
        System.out.println("--- Example 2: With Volatile (Fixed) ---\n");

        VolatileRunner volatileRunner = new VolatileRunner();
        Thread volatileWorker = new Thread(volatileRunner);
        volatileWorker.start();

        Thread.sleep(100);
        volatileRunner.setRunning(false);
        System.out.println("Set running = false (with volatile)");
        System.out.println("Worker will stop soon...\n");

        volatileWorker.join(1000);
        System.out.println("Worker stopped correctly!\n");

        // ============================================================
        // EXAMPLE 3: volatile is NOT atomic
        // ============================================================
        System.out.println("--- Example 3: volatile is NOT Atomic ---\n");

        VolatileCounter vc = new VolatileCounter();

        Thread inc1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) vc.increment();
        });

        Thread inc2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) vc.increment();
        });

        inc1.start();
        inc2.start();
        inc1.join();
        inc2.join();

        System.out.println("Volatile counter: " + vc.getCount());
        System.out.println("Expected: 2000");
        System.out.println("(volatile doesn't make ++ atomic!)\n");

        // ============================================================
        // EXAMPLE 4: volatile for double-checked locking
        // ============================================================
        System.out.println("--- Example 4: Double-Checked Locking ---\n");

        // Without volatile (broken)
        System.out.println("Without volatile: instance may be partially constructed");

        // With volatile (correct)
        VolatileSingleton singleton = VolatileSingleton.getInstance();
        System.out.println("Got singleton: " + singleton);
        System.out.println();

        // ============================================================
        // EXAMPLE 5: volatile with object references
        // ============================================================
        System.out.println("--- Example 5: volatile References ---\n");

        Holder holder = new Holder();
        holder.value = new Data(42);

        Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                while (holder.value == null) {
                    // Wait
                }
                System.out.println("Reader saw value: " + holder.value.value);
            }
        });

        Thread writer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                holder.value = new Data(100);
                System.out.println("Writer set value");
            }
        });

        reader.start();
        writer.start();
        reader.join();
        writer.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 6: When to use volatile
        // ============================================================
        System.out.println("--- Example 6: When to Use volatile ---\n");

        System.out.println("Use volatile for:");
        System.out.println("1. Simple flags (stop, pause, ready)");
        System.out.println("2. One-time initialization (with double-check)");
        System.out.println("3. State indicators");
        System.out.println();
        System.out.println("Don't use volatile for:");
        System.out.println("1. Compound operations (i++, count += 1)");
        System.out.println("2. Operations requiring mutual exclusion");
        System.out.println("3. Check-then-act patterns");
        System.out.println("4. When you need atomicity");
    }

    // ============================================================
    // EXAMPLE CLASSES
    // ============================================================

    static class NoVolatileRunner implements Runnable {
        private boolean running = true;

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            int count = 0;
            while (running) {
                count++;
                // Busy wait
            }
            System.out.println("NoVolatileRunner stopped at count: " + count);
        }
    }

    static class VolatileRunner implements Runnable {
        private volatile boolean running = true;

        public void setRunning(boolean running) {
            this.running = running;
        }

        @Override
        public void run() {
            int count = 0;
            while (running) {
                count++;
            }
            System.out.println("VolatileRunner stopped at count: " + count);
        }
    }

    static class VolatileCounter {
        private volatile int count = 0;

        public void increment() {
            count++;  // NOT atomic despite volatile!
        }

        public int getCount() {
            return count;
        }
    }

    static class VolatileSingleton {
        private static volatile VolatileSingleton instance;

        private VolatileSingleton() {}

        public static VolatileSingleton getInstance() {
            if (instance == null) {  // First check (no lock)
                synchronized (VolatileSingleton.class) {
                    if (instance == null) {  // Second check (with lock)
                        instance = new VolatileSingleton();
                    }
                }
            }
            return instance;
        }
    }

    static class Holder {
        public volatile Data value;
    }

    static class Data {
        public final int value;
        public Data(int value) {
            this.value = value;
        }
    }
}
