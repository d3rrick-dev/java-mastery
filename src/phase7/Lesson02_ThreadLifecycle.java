package phase7;

/**
 * LESSON 2: THREAD LIFECYCLE
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * A thread goes through distinct states from birth to death.
 * Understanding these states helps you debug threading issues.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Threads need a well-defined lifecycle for the JVM to manage them
 * - Different states allow different operations (can't start a dead thread)
 * - Helps with debugging and monitoring
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Restaurant kitchen:
 * - NEW: Chef hired but not yet started
 * - RUNNABLE: Chef actively cooking
 * - BLOCKED: Chef waiting for the oven
 * - WAITING: Chef waiting for ingredients from storage
 * - TIMED_WAITING: Chef waiting 5 minutes for delivery
 * - TERMINATED: Chef's shift ended
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Database connection pool:
 * - NEW: Connection object created but not yet used
 * - RUNNABLE: Connection actively executing a query
 * - BLOCKED: Connection waiting for database lock
 * - WAITING: Connection waiting in pool for assignment
 * - TERMINATED: Connection closed and returned to pool
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What are the different states of a thread in Java?"
 * Answer: NEW, RUNNABLE, BLOCKED, WAITING, TIMED_WAITING, TERMINATED
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Calling start() twice on same thread (IllegalThreadStateException)
 * - Not understanding that RUNNABLE includes waiting for CPU
 * - Assuming BLOCKED and WAITING are the same
 * - Forgetting to handle InterruptedException properly
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Too many BLOCKED threads = contention problem
 * - Too many WAITING threads = possible deadlock
 * - Thread state monitoring helps identify bottlenecks
 *
 * ============================================================
 * THREAD STATE DIAGRAM
 * ============================================================
 *
 *   +-------+
 *   |  NEW  |  <-- Thread created but not started
 *   +---+---+
 *       |
 *       | start()
 *       v
 *   +---+---+
 *   |RUNNABLE|  <-- Executing or ready to execute
 *   +---+----+
 *       |
 *       | (various conditions)
 *       +------------------+
 *       |                  |
 *       v                  v
 *   +-------+         +---------+
 *   |BLOCKED|         | WAITING |  <-- Waiting indefinitely
 *   +---+---+         +----+----+
 *       |                  |
 *       | (lock acquired)  | (notify/notifyAll/interrupt)
 *       |                  |
 *       v                  v
 *   +-------+         +---------+
 *   |RUNNABLE|        |RUNNABLE |
 *   +-------+         +---------+
 *       |
 *       | (timeout expires or interrupt)
 *       v
 *   +------------+
 *   |TIMED_WAITING|  <-- Waiting with timeout
 *   +-----+------+
 *         |
 *         | (timeout or interrupt)
 *         v
 *   +-------+
 *   |RUNNABLE|
 *   +---+----+
 *       |
 *       | run() completes
 *       v
 *   +-----------+
 *   |TERMINATED |  <-- Thread has finished
 *   +-----------+
 */

public class Lesson02_ThreadLifecycle {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== THREAD LIFECYCLE ===\n");

        // ============================================================
        // EXAMPLE 1: Demonstrating all thread states
        // ============================================================
        System.out.println("--- Example 1: Thread States ---\n");

        // 1. NEW state
        Thread newThread = new Thread(() -> {
            System.out.println("Thread running: " + Thread.currentThread().getName());
        });
        System.out.println("1. NEW: " + newThread.getState());

        // 2. Start thread -> RUNNABLE
        newThread.start();
        System.out.println("2. After start(): " + newThread.getState());

        // Wait for it to finish
        newThread.join();
        System.out.println("3. After join(): " + newThread.getState());
        System.out.println();

        // ============================================================
        // EXAMPLE 2: BLOCKED state demonstration
        // ============================================================
        System.out.println("--- Example 2: BLOCKED State ---\n");

        Object lock = new Object();

        Thread blockingThread = new Thread(() -> {
            synchronized (lock) {
                try {
                    // Hold lock for a while
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "BlockingThread");

        Thread blockedThread = new Thread(() -> {
            synchronized (lock) {
                // This will block until blockingThread releases lock
                System.out.println("Got the lock!");
            }
        }, "BlockedThread");

        blockingThread.start();
        // Give blockingThread time to acquire lock
        Thread.sleep(100);

        blockedThread.start();
        // Give blockedThread time to try acquiring lock
        Thread.sleep(100);

        System.out.println("BlockingThread state: " + blockingThread.getState());
        System.out.println("BlockedThread state: " + blockedThread.getState());

        blockingThread.join();
        blockedThread.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 3: WAITING state demonstration
        // ============================================================
        System.out.println("--- Example 3: WAITING State ---\n");

        Object waitLock = new Object();

        Thread waitingThread = new Thread(() -> {
            synchronized (waitLock) {
                try {
                    waitLock.wait();  // Wait indefinitely
                } catch (InterruptedException e) {
                    System.out.println("Waiting thread was interrupted!");
                    Thread.currentThread().interrupt();
                }
            }
        }, "WaitingThread");

        waitingThread.start();
        Thread.sleep(100);

        System.out.println("WaitingThread state: " + waitingThread.getState());

        // Notify the waiting thread
        synchronized (waitLock) {
            waitLock.notify();
        }

        waitingThread.join();
        System.out.println("WaitingThread state after notify: " + waitingThread.getState());
        System.out.println();

        // ============================================================
        // EXAMPLE 4: TIMED_WAITING state demonstration
        // ============================================================
        System.out.println("--- Example 4: TIMED_WAITING State ---\n");

        Thread timedWaitingThread = new Thread(() -> {
            try {
                Thread.sleep(1000);  // Wait with timeout
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "TimedWaitingThread");

        timedWaitingThread.start();
        Thread.sleep(100);

        System.out.println("TimedWaitingThread state: " + timedWaitingThread.getState());

        timedWaitingThread.join();
        System.out.println("TimedWaitingThread state after sleep: " + timedWaitingThread.getState());
        System.out.println();

        // ============================================================
        // EXAMPLE 5: TERMINATED state
        // ============================================================
        System.out.println("--- Example 5: TERMINATED State ---\n");

        Thread terminatedThread = new Thread(() -> {
            System.out.println("Thread finishing...");
        }, "TerminatedThread");

        terminatedThread.start();
        terminatedThread.join();

        System.out.println("TerminatedThread state: " + terminatedThread.getState());
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Thread state monitoring utility
        // ============================================================
        System.out.println("--- Example 6: Monitoring Thread States ---\n");

        ThreadGroup group = Thread.currentThread().getThreadGroup();
        int activeCount = group.activeCount();
        Thread[] threads = new Thread[activeCount];
        group.enumerate(threads);

        System.out.println("Active threads in main group: " + activeCount);
        for (Thread t : threads) {
            if (t != null) {
                System.out.println("  " + t.getName() + ": " + t.getState());
            }
        }
    }
}
