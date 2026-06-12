package phase7;

/**
 * LESSON 3: CREATING THREADS - EXTENDING THREAD
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * You can create a thread by extending the Thread class and overriding its run() method.
 * This makes your class a "thread" itself.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Provides a direct way to create threads
 * - run() method defines what the thread does
 * - start() method begins thread execution
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * A download manager where each download is a separate thread:
 * - Each DownloadThread extends Thread
 * - Each has its own run() method for downloading
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Scheduled task in a backend service:
 * - HealthCheckThread extends Thread
 * - Runs periodically to check service health
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "How do you create a thread in Java?"
 * Answer: Extend Thread class OR implement Runnable
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Calling run() directly instead of start() (runs in same thread!)
 * - Extending Thread when you should implement Runnable (single inheritance limitation)
 * - Not handling InterruptedException
 * - Creating too many threads (resource exhaustion)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Each Thread object consumes ~1MB stack memory (default)
 * - Creating thousands of threads causes OutOfMemoryError
 * - Context switching overhead increases with thread count
 *
 * ============================================================
 * BROKEN IMPLEMENTATION
 * ============================================================
 *
 *   // WRONG: Calling run() directly
 *   Thread t = new MyThread();
 *   t.run();  // Runs in CURRENT thread, not new thread!
 *
 *   // WRONG: Not handling interruption
 *   public void run() {
 *       while (true) {  // Never stops!
 *           doWork();
 *       }
 *   }
 *
 * ============================================================
 * CORRECTED IMPLEMENTATION
 * ============================================================
 *
 *   // CORRECT: Call start()
 *   Thread t = new MyThread();
 *   t.start();  // Creates new OS thread
 *
 *   // CORRECT: Handle interruption
 *   public void run() {
 *       while (!Thread.currentThread().isInterrupted()) {
 *           doWork();
 *       }
 *   }
 */

public class Lesson03_CreatingThreads_ExtendingThread {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== CREATING THREADS: EXTENDING THREAD ===\n");

        // ============================================================
        // EXAMPLE 1: Basic thread creation by extending Thread
        // ============================================================
        System.out.println("--- Example 1: Basic Thread Extension ---\n");

        MyThread thread1 = new MyThread("Worker-1");
        MyThread thread2 = new MyThread("Worker-2");

        System.out.println("Starting threads...");
        thread1.start();  // Creates new thread
        thread2.start();

        // Wait for both to complete
        thread1.join();
        thread2.join();

        System.out.println("All threads completed!\n");

        // ============================================================
        // EXAMPLE 2: Thread with shared data
        // ============================================================
        System.out.println("--- Example 2: Threads with Shared Data ---\n");

        SharedData shared = new SharedData();

        CounterThread counter1 = new CounterThread(shared);
        CounterThread counter2 = new CounterThread(shared);

        counter1.start();
        counter2.start();

        counter1.join();
        counter2.join();

        System.out.println("Final count: " + shared.getCount());
        System.out.println("(Race condition may cause incorrect result)\n");

        // ============================================================
        // EXAMPLE 3: Thread priority demonstration
        // ============================================================
        System.out.println("--- Example 3: Thread Priority ---\n");

        PriorityThread lowPriority = new PriorityThread("LowPriority", Thread.MIN_PRIORITY);
        PriorityThread highPriority = new PriorityThread("HighPriority", Thread.MAX_PRIORITY);

        lowPriority.start();
        highPriority.start();

        lowPriority.join();
        highPriority.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 4: Daemon threads
        // ============================================================
        System.out.println("--- Example 4: Daemon Threads ---\n");

        DaemonThread daemon = new DaemonThread("DaemonWorker");
        daemon.setDaemon(true);  // Daemon thread dies when main thread ends

        daemon.start();
        System.out.println("Main thread sleeping for 2 seconds...");
        Thread.sleep(2000);
        System.out.println("Main thread ending - daemon will die too!\n");

        // ============================================================
        // EXAMPLE 5: Thread interruption
        // ============================================================
        System.out.println("--- Example 5: Thread Interruption ---\n");

        InterruptibleThread interruptible = new InterruptibleThread("Interruptible");
        interruptible.start();

        Thread.sleep(500);
        System.out.println("Interrupting thread...");
        interruptible.interrupt();

        interruptible.join();
        System.out.println("Thread was interrupted and stopped gracefully.\n");

        // ============================================================
        // EXAMPLE 6: When NOT to extend Thread
        // ============================================================
        System.out.println("--- Example 6: Why NOT to Extend Thread ---\n");
        System.out.println("Problems with extending Thread:");
        System.out.println("1. You can't extend another class (single inheritance)");
        System.out.println("2. Thread and task are coupled together");
        System.out.println("3. Can't reuse the same task with different threads");
        System.out.println("4. Less flexible than Runnable/Callable");
        System.out.println();
        System.out.println("Better approach: Implement Runnable or Callable");
    }

    // ============================================================
    // EXAMPLE THREAD CLASSES
    // ============================================================

    /**
     * Basic thread by extending Thread.
     */
    static class MyThread extends Thread {
        private final String name;

        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + " is running in: " + Thread.currentThread().getName());
            try {
                // Simulate work
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println(name + " completed");
        }
    }

    /**
     * Thread that increments a shared counter.
     * Demonstrates race condition (will be fixed in Lesson 11).
     */
    static class CounterThread extends Thread {
        private final SharedData sharedData;

        public CounterThread(SharedData sharedData) {
            this.sharedData = sharedData;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                sharedData.increment();
            }
        }
    }

    /**
     * Thread demonstrating priority.
     */
    static class PriorityThread extends Thread {
        public PriorityThread(String name, int priority) {
            super(name);
            setPriority(priority);
        }

        @Override
        public void run() {
            int count = 0;
            while (count < 1000000) {
                count++;
            }
            System.out.println(getName() + " (priority=" + getPriority() + ") completed");
        }
    }

    /**
     * Daemon thread example.
     */
    static class DaemonThread extends Thread {
        public DaemonThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            int count = 0;
            while (count < 10) {
                System.out.println(getName() + " working... " + count);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                count++;
            }
        }
    }

    /**
     * Thread that responds to interruption.
     */
    static class InterruptibleThread extends Thread {
        public InterruptibleThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println(getName() + " started, will run until interrupted");

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    // Simulate work
                    Thread.sleep(100);
                    System.out.println(getName() + " working...");
                } catch (InterruptedException e) {
                    System.out.println(getName() + " caught InterruptedException!");
                    // Restore interrupted status
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            System.out.println(getName() + " stopped gracefully");
        }
    }

    /**
     * Shared data class (not thread-safe).
     */
    static class SharedData {
        private int count = 0;

        public void increment() {
            count++;  // NOT thread-safe!
        }

        public int getCount() {
            return count;
        }
    }
}
