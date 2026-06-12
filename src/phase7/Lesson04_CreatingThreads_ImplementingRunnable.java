package phase7;

/**
 * LESSON 4: CREATING THREADS - IMPLEMENTING RUNNABLE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * Runnable is a functional interface with a single method: run().
 * You implement Runnable to define what a thread should do,
 * then pass it to a Thread constructor.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Separates the TASK (Runnable) from the THREAD (Thread)
 * - Allows single inheritance (your class can extend another class)
 * - More flexible - same Runnable can be used with different Threads
 * - Better for thread pools and executors
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Email sending service:
 * - EmailTask implements Runnable (the task)
 * - Can be run by different threads (Thread, Executor, etc.)
 * - Same task can be reused
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * API request handler:
 * - RequestHandler implements Runnable
 * - Thread pool workers execute RequestHandler tasks
 * - Same handler logic used across all worker threads
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What's the difference between extending Thread and implementing Runnable?"
 * Answer: Runnable separates task from thread, allows single inheritance,
 *         better for thread pools
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Creating new Thread for each Runnable (defeats the purpose)
 * - Not using thread pools (resource waste)
 * - Forgetting that Runnable.run() doesn't return a value
 * - Not handling exceptions in run() method
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Runnable itself is lightweight (just an object)
 * - Thread creation is expensive (use with Executor)
 * - Runnable can be reused multiple times
 *
 * ============================================================
 * COMPARISON: EXTEND THREAD vs IMPLEMENT RUNNABLE
 * ============================================================
 *
 *   EXTEND THREAD              IMPLEMENT RUNNABLE
 *   +----------------+         +----------------+
 *   | class MyThread |         | class MyTask   |
 *   | extends Thread |         | implements     |
 *   |                |         | Runnable       |
 *   +----------------+         +----------------+
 *   | - run()        |         | - run()        |
 *   | - start()      |         |                |
 *   +----------------+         +----------------+
 *          |                           |
 *          v                           v
 *   MyThread t = new           Thread t = new
 *          MyThread();                  Thread(
 *   t.start();                  new MyTask());
 *                                t.start();
 *
 *   Pros: Simple                Pros: Flexible
 *   Cons: Single inheritance    Cons: Slightly more code
 */

public class Lesson04_CreatingThreads_ImplementingRunnable {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== CREATING THREADS: IMPLEMENTING RUNNABLE ===\n");

        // ============================================================
        // EXAMPLE 1: Basic Runnable implementation
        // ============================================================
        System.out.println("--- Example 1: Basic Runnable ---\n");

        Runnable task1 = new MyRunnable("Task-1");
        Runnable task2 = new MyRunnable("Task-2");

        Thread thread1 = new Thread(task1);
        Thread thread2 = new Thread(task2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("All tasks completed!\n");

        // ============================================================
        // EXAMPLE 2: Lambda expression for Runnable
        // ============================================================
        System.out.println("--- Example 2: Lambda Expression ---\n");

        Runnable lambdaTask = () -> {
            System.out.println("Lambda task running in: " + Thread.currentThread().getName());
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        Thread lambdaThread = new Thread(lambdaTask);
        lambdaThread.start();
        lambdaThread.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Reusing the same Runnable
        // ============================================================
        System.out.println("--- Example 3: Reusing Runnable ---\n");

        // Same task, different threads
        Runnable reusableTask = () -> {
            String threadName = Thread.currentThread().getName();
            System.out.println(threadName + " executing task");
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };

        // Run with 3 different threads
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(reusableTask, "Worker-" + i);
            t.start();
            t.join();
        }
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Runnable with shared state
        // ============================================================
        System.out.println("--- Example 4: Runnable with Shared State ---\n");

        SharedState state = new SharedState();

        Runnable stateTask = () -> {
            for (int i = 0; i < 5; i++) {
                state.increment();
                System.out.println(Thread.currentThread().getName() +
                    " - count: " + state.getCount());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        Thread t1 = new Thread(stateTask, "Thread-A");
        Thread t2 = new Thread(stateTask, "Thread-B");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final count: " + state.getCount());
        System.out.println("(Race condition may cause incorrect result)\n");

        // ============================================================
        // EXAMPLE 5: Runnable in thread pool (preview)
        // ============================================================
        System.out.println("--- Example 5: Runnable with Executor (Preview) ---\n");

        java.util.concurrent.ExecutorService executor =
            java.util.concurrent.Executors.newFixedThreadPool(2);

        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            executor.execute(() -> {
                System.out.println("Task " + taskId + " executed by " +
                    Thread.currentThread().getName());
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.sleep(100);
        }
        System.out.println("All executor tasks completed!\n");

        // ============================================================
        // EXAMPLE 6: Runnable limitations
        // ============================================================
        System.out.println("--- Example 6: Runnable Limitations ---\n");
        System.out.println("Runnable limitations:");
        System.out.println("1. Cannot return a value (use Callable instead)");
        System.out.println("2. Cannot throw checked exceptions");
        System.out.println("3. No built-in way to check completion status");
        System.out.println();
        System.out.println("Solution: Use Callable + Future for return values");
    }

    // ============================================================
    // EXAMPLE RUNNABLE CLASSES
    // ============================================================

    /**
     * Basic Runnable implementation.
     */
    static class MyRunnable implements Runnable {
        private final String name;

        public MyRunnable(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " running in: " + Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(name + " completed");
        }
    }

    /**
     * Shared state for demonstration.
     */
    static class SharedState {
        private int count = 0;

        public void increment() {
            count++;  // NOT thread-safe!
        }

        public int getCount() {
            return count;
        }
    }
}
