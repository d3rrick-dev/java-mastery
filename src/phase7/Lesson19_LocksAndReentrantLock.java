package phase7;

import java.util.concurrent.locks.*;

/**
 * LESSON 19: LOCKS AND REENTRANTLOCK
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * ReentrantLock is an explicit lock that provides more flexibility
 * than synchronized. You explicitly acquire and release the lock,
 * and it supports advanced features like tryLock, fairness, and
 * interruptible lock acquisition.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - synchronized has limitations (no tryLock, no fairness)
 * - Need interruptible lock acquisition
 * - Need timeout on lock acquisition
 * - Need multiple condition variables
 * - Need fair locking (FIFO order)
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Database connection pool:
 * - Thread tries to get connection (tryLock with timeout)
 * - If can't get in 5 seconds, give up
 * - If interrupted while waiting, stop waiting
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Cache with fair access:
 * - Multiple threads updating cache
 * - Fair lock ensures no thread starves
 * - tryLock prevents indefinite waiting
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is ReentrantLock?"
 * Answer: Explicit lock with tryLock, fairness, and
 *         interruptible acquisition
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Forgetting to unlock in finally block
 * - Using fair locks when not needed (performance hit)
 * - Not handling InterruptedException from lockInterruptibly
 * - Locking too much code (reduce critical section)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - ReentrantLock: slightly faster than synchronized
 * - Fair lock: 10-20% slower than non-fair
 * - tryLock: non-blocking, very fast
 * - Condition variables: more efficient than wait/notify
 *
 * ============================================================
 * SYNCHRONIZED vs REENTRANTLOCK
 * ============================================================
 *
 *   synchronized              ReentrantLock
 *   +----------------+        +----------------+
 *   | Automatic lock |        | Manual lock()   |
 *   | Auto unlock    |        | Must unlock()   |
 *   | No timeout     |        | tryLock(timeout)|
 *   | Non-fair only  |        | Fair option     |
 *   | 1 condition    |        | Multiple cond.  |
 *   | Blocking only  |        | Interruptible   |
 *   +----------------+        +----------------+
 */

public class Lesson19_LocksAndReentrantLock {

    public static void main(String[] args) throws Exception {
        System.out.println("=== LOCKS AND REENTRANTLOCK ===\n");

        // ============================================================
        // EXAMPLE 1: Basic ReentrantLock
        // ============================================================
        System.out.println("--- Example 1: Basic ReentrantLock ---\n");

        ReentrantLock lock = new ReentrantLock();
        SharedCounter counter = new SharedCounter();

        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 1000; i++) {
                    counter.increment();
                }
            } finally {
                lock.unlock();
            }
        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                for (int i = 0; i < 1000; i++) {
                    counter.increment();
                }
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Count: " + counter.getCount() + " (expected 2000)\n");

        // ============================================================
        // EXAMPLE 2: tryLock (non-blocking)
        // ============================================================
        System.out.println("--- Example 2: tryLock ---\n");

        ReentrantLock tryLock = new ReentrantLock();

        Thread holder = new Thread(() -> {
            tryLock.lock();
            try {
                System.out.println("Holder: Got lock, sleeping...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                tryLock.unlock();
                System.out.println("Holder: Released lock");
            }
        });

        holder.start();
        Thread.sleep(100);

        Thread tryAcquirer = new Thread(() -> {
            boolean acquired = tryLock.tryLock();
            if (acquired) {
                try {
                    System.out.println("Acquirer: Got lock!");
                } finally {
                    tryLock.unlock();
                }
            } else {
                System.out.println("Acquirer: Couldn't get lock, doing other work");
            }
        });

        tryAcquirer.start();
        tryAcquirer.join();
        holder.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 3: tryLock with timeout
        // ============================================================
        System.out.println("--- Example 3: tryLock with Timeout ---\n");

        ReentrantLock timeoutLock = new ReentrantLock();

        Thread holder2 = new Thread(() -> {
            timeoutLock.lock();
            try {
                System.out.println("Timeout holder: Got lock, sleeping 2s...");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                timeoutLock.unlock();
            }
        });

        holder2.start();
        Thread.sleep(100);

        Thread timeoutAcquirer = new Thread(() -> {
            try {
                boolean acquired = timeoutLock.tryLock(1, java.util.concurrent.TimeUnit.SECONDS);
                if (acquired) {
                    try {
                        System.out.println("Timeout acquirer: Got lock!");
                    } finally {
                        timeoutLock.unlock();
                    }
                } else {
                    System.out.println("Timeout acquirer: Timed out waiting");
                }
            } catch (InterruptedException e) {
                System.out.println("Timeout acquirer: Interrupted");
                Thread.currentThread().interrupt();
            }
        });

        timeoutAcquirer.start();
        timeoutAcquirer.join();
        holder2.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 4: lockInterruptibly (interruptible acquisition)
        // ============================================================
        System.out.println("--- Example 4: lockInterruptibly ---\n");

        ReentrantLock interruptibleLock = new ReentrantLock();

        Thread holder3 = new Thread(() -> {
            interruptibleLock.lock();
            try {
                System.out.println("Interruptible holder: Got lock, sleeping...");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                interruptibleLock.unlock();
            }
        });

        holder3.start();
        Thread.sleep(100);

        Thread interruptibleAcquirer = new Thread(() -> {
            try {
                System.out.println("Interruptible acquirer: Waiting for lock...");
                interruptibleLock.lockInterruptibly();
                try {
                    System.out.println("Interruptible acquirer: Got lock!");
                } finally {
                    interruptibleLock.unlock();
                }
            } catch (InterruptedException e) {
                System.out.println("Interruptible acquirer: Was interrupted!");
                Thread.currentThread().interrupt();
            }
        });

        interruptibleAcquirer.start();
        Thread.sleep(500);
        System.out.println("Interrupting acquirer...");
        interruptibleAcquirer.interrupt();
        interruptibleAcquirer.join();
        holder3.interrupt();
        holder3.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 5: Fair lock
        // ============================================================
        System.out.println("--- Example 5: Fair Lock ---\n");

        ReentrantLock fairLock = new ReentrantLock(true); // fair = true

        for (int i = 1; i <= 3; i++) {
            final int id = i;
            new Thread(() -> {
                fairLock.lock();
                try {
                    System.out.println("Fair thread " + id + " got lock");
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    fairLock.unlock();
                }
            }).start();
        }

        Thread.sleep(500);
        System.out.println();

        // ============================================================
        // EXAMPLE 6: Condition variables
        // ============================================================
        System.out.println("--- Example 6: Condition Variables ---\n");

        ReentrantLock conditionLock = new ReentrantLock();
        Condition condition = conditionLock.newCondition();
        boolean[] ready = {false};

        Thread waiter = new Thread(() -> {
            conditionLock.lock();
            try {
                while (!ready[0]) {
                    System.out.println("Waiter: Waiting for signal...");
                    condition.await();
                }
                System.out.println("Waiter: Received signal, proceeding!");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                conditionLock.unlock();
            }
        });

        Thread signaller = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            conditionLock.lock();
            try {
                ready[0] = true;
                System.out.println("Signaller: Sending signal...");
                condition.signal();
            } finally {
                conditionLock.unlock();
            }
        });

        waiter.start();
        signaller.start();
        waiter.join();
        signaller.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 7: Multiple conditions
        // ============================================================
        System.out.println("--- Example 7: Multiple Conditions ---\n");

        ReentrantLock multiCondLock = new ReentrantLock();
        Condition notEmpty = multiCondLock.newCondition();
        Condition notFull = multiCondLock.newCondition();

        Buffer buffer = new Buffer(5, notEmpty, notFull);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                buffer.put(i);
            }
        });

        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                buffer.take();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println();
    }

    // ============================================================
    // HELPER CLASSES
    // ============================================================

    static class SharedCounter {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }

    static class Buffer {
        private final int[] items;
        private int count = 0;
        private final Condition notEmpty;
        private final Condition notFull;

        public Buffer(int size, Condition notEmpty, Condition notFull) {
            this.items = new int[size];
            this.notEmpty = notEmpty;
            this.notFull = notFull;
        }

        public void put(int value) {
            try {
                notFull.await();  // Wait if full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            items[count++] = value;
            System.out.println("Produced: " + value);
            notEmpty.signal();
        }

        public int take() {
            try {
                notEmpty.await();  // Wait if empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            int value = items[--count];
            System.out.println("Consumed: " + value);
            notFull.signal();
            return value;
        }
    }
}
