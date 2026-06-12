package phase7;

/**
 * LESSON 14: DEADLOCKS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * A deadlock occurs when two or more threads are blocked forever,
 * each waiting for the other to release a lock. Like two people
 * each holding a key the other needs.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Multiple locks held by different threads
 * - Circular wait condition
 * - No preemption (threads won't release locks voluntarily)
 * - Mutual exclusion (only one thread can hold lock)
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Two people at a narrow bridge:
 * - Person A is on north side, needs south key
 * - Person B is on south side, needs north key
 * - Neither can move = deadlock
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Database transactions:
 * - Transaction A locks row 1, wants row 2
 * - Transaction B locks row 2, wants row 1
 * - Both wait forever = deadlock
 * - Database detects and rolls back one
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is a deadlock?"
 * Answer: When threads are blocked forever, each waiting for
 *         resources held by others
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Nested synchronized blocks
 * - Acquiring locks in different orders
 * - Calling external methods while holding lock
 * - Not using tryLock with timeout
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - Deadlocked threads waste resources
 * - Application may appear frozen
 * - Detection is expensive
 * - Prevention is better than cure
 *
 * ============================================================
 * DEADLOCK CONDITIONS (ALL 4 MUST EXIST)
 * ============================================================
 *
 *   1. Mutual Exclusion    - Only one thread can hold lock
 *   2. Hold and Wait        - Thread holds lock, waits for another
 *   3. No Preemption        - Locks not forcibly taken
 *   4. Circular Wait        - A waits B, B waits A
 *
 *   To prevent: Break ANY one condition
 */

public class Lesson14_Deadlocks {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== DEADLOCKS ===\n");

        // ============================================================
        // EXAMPLE 1: Classic deadlock
        // ============================================================
        System.out.println("--- Example 1: Classic Deadlock ---\n");

        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Thread 1: Holding lock 1...");
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                System.out.println("Thread 1: Waiting for lock 2...");
                synchronized (lock2) {
                    System.out.println("Thread 1: Got both locks!");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Thread 2: Holding lock 2...");
                try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

                System.out.println("Thread 2: Waiting for lock 1...");
                synchronized (lock1) {
                    System.out.println("Thread 2: Got both locks!");
                }
            }
        });

        thread1.start();
        thread2.start();

        // Wait a bit to see deadlock
        Thread.sleep(500);

        System.out.println("Thread 1 state: " + thread1.getState());
        System.out.println("Thread 2 state: " + thread2.getState());
        System.out.println("DEADLOCK DETECTED!\n");

        // Interrupt to exit
        thread1.interrupt();
        thread2.interrupt();
        thread1.join();
        thread2.join();
        System.out.println();

        // ============================================================
        // EXAMPLE 2: Fixed deadlock (consistent lock ordering)
        // ============================================================
        System.out.println("--- Example 2: Fixed Deadlock ---\n");

        Object fixedLock1 = new Object();
        Object fixedLock2 = new Object();

        Thread fixedThread1 = new Thread(() -> {
            // Always acquire locks in same order
            synchronized (fixedLock1) {
                System.out.println("Fixed Thread 1: Holding lock 1...");
                synchronized (fixedLock2) {
                    System.out.println("Fixed Thread 1: Got both locks!");
                }
            }
        });

        Thread fixedThread2 = new Thread(() -> {
            // Same order: lock1 first, then lock2
            synchronized (fixedLock1) {
                System.out.println("Fixed Thread 2: Holding lock 1...");
                synchronized (fixedLock2) {
                    System.out.println("Fixed Thread 2: Got both locks!");
                }
            }
        });

        fixedThread1.start();
        fixedThread2.start();
        fixedThread1.join();
        fixedThread2.join();
        System.out.println("No deadlock - both completed!\n");

        // ============================================================
        // EXAMPLE 3: Using tryLock with timeout
        // ============================================================
        System.out.println("--- Example 3: tryLock with Timeout ---\n");

        java.util.concurrent.locks.ReentrantLock tryLock1 = new java.util.concurrent.locks.ReentrantLock();
        java.util.concurrent.locks.ReentrantLock tryLock2 = new java.util.concurrent.locks.ReentrantLock();

        Thread tryThread1 = new Thread(() -> {
            boolean locked1 = false;
            boolean locked2 = false;

            try {
                locked1 = tryLock1.tryLock(1, java.util.concurrent.TimeUnit.SECONDS);
                if (locked1) {
                    System.out.println("Try Thread 1: Got lock 1");
                    Thread.sleep(100);

                    locked2 = tryLock2.tryLock(1, java.util.concurrent.TimeUnit.SECONDS);
                    if (locked2) {
                        System.out.println("Try Thread 1: Got lock 2");
                    } else {
                        System.out.println("Try Thread 1: Couldn't get lock 2, releasing lock 1");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (locked2) tryLock2.unlock();
                if (locked1) tryLock1.unlock();
            }
        });

        Thread tryThread2 = new Thread(() -> {
            boolean locked2 = false;
            boolean locked1 = false;

            try {
                locked2 = tryLock2.tryLock(1, java.util.concurrent.TimeUnit.SECONDS);
                if (locked2) {
                    System.out.println("Try Thread 2: Got lock 2");
                    Thread.sleep(100);

                    locked1 = tryLock1.tryLock(1, java.util.concurrent.TimeUnit.SECONDS);
                    if (locked1) {
                        System.out.println("Try Thread 2: Got lock 1");
                    } else {
                        System.out.println("Try Thread 2: Couldn't get lock 1, releasing lock 2");
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                if (locked1) tryLock1.unlock();
                if (locked2) tryLock2.unlock();
            }
        });

        tryThread1.start();
        tryThread2.start();
        tryThread1.join();
        tryThread2.join();
        System.out.println("No deadlock with tryLock!\n");

        // ============================================================
        // EXAMPLE 4: Deadlock detection
        // ============================================================
        System.out.println("--- Example 4: Deadlock Detection ---\n");

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        long[] threadIds = bean.findDeadlockedThreads();

        if (threadIds != null) {
            System.out.println("Deadlocked threads found:");
            for (long id : threadIds) {
                ThreadInfo info = bean.getThreadInfo(id);
                System.out.println("  " + info.getThreadName());
            }
        } else {
            System.out.println("No deadlocks detected\n");
        }

        // ============================================================
        // EXAMPLE 5: Bank transfer deadlock scenario
        // ============================================================
        System.out.println("--- Example 5: Bank Transfer Deadlock ---\n");

        BankAccount accountA = new BankAccount("A", 1000);
        BankAccount accountB = new BankAccount("B", 1000);

        Thread transferAtoB = new Thread(() -> {
            transfer(accountA, accountB, 100);
        });

        Thread transferBtoA = new Thread(() -> {
            transfer(accountB, accountA, 100);
        });

        transferAtoB.start();
        transferBtoA.start();

        Thread.sleep(500);

        System.out.println("Account A: " + accountA.getBalance());
        System.out.println("Account B: " + accountB.getBalance());
        System.out.println("(May be deadlocked!)\n");

        transferAtoB.interrupt();
        transferBtoA.interrupt();
        transferAtoB.join();
        transferBtoA.join();

        // ============================================================
        // EXAMPLE 6: Fixed bank transfer
        // ============================================================
        System.out.println("--- Example 6: Fixed Bank Transfer ---\n");

        BankAccount fixedA = new BankAccount("A", 1000);
        BankAccount fixedB = new BankAccount("B", 1000);

        Thread fixedTransferAtoB = new Thread(() -> {
            safeTransfer(fixedA, fixedB, 100);
        });

        Thread fixedTransferBtoA = new Thread(() -> {
            safeTransfer(fixedB, fixedA, 100);
        });

        fixedTransferAtoB.start();
        fixedTransferBtoA.start();
        fixedTransferAtoB.join();
        fixedTransferBtoA.join();

        System.out.println("Fixed Account A: " + fixedA.getBalance());
        System.out.println("Fixed Account B: " + fixedB.getBalance());
        System.out.println("No deadlock!\n");

        // ============================================================
        // EXAMPLE 7: Deadlock prevention strategies
        // ============================================================
        System.out.println("--- Example 7: Prevention Strategies ---\n");

        System.out.println("1. Consistent Lock Ordering:");
        System.out.println("   Always acquire locks in same order\n");

        System.out.println("2. Lock Timeout (tryLock):");
        System.out.println("   Give up if can't acquire within time\n");

        System.out.println("3. Lock Striping:");
        System.out.println("   Use multiple locks for different data\n");

        System.out.println("4. Avoid Nested Locks:");
        System.out.println("   Don't hold lock while calling external code\n");

        System.out.println("5. Use Open Calls:");
        System.out.println("   Release lock before calling other methods\n");
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    static void transfer(BankAccount from, BankAccount to, int amount) {
        synchronized (from) {
            System.out.println(Thread.currentThread().getName() + ": Locked " + from.getName());
            try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

            synchronized (to) {
                System.out.println(Thread.currentThread().getName() + ": Locked " + to.getName());
                from.withdraw(amount);
                to.deposit(amount);
                System.out.println(Thread.currentThread().getName() + ": Transfer complete");
            }
        }
    }

    static void safeTransfer(BankAccount from, BankAccount to, int amount) {
        // Consistent ordering: compare account names
        BankAccount first = from.getName().compareTo(to.getName()) < 0 ? from : to;
        BankAccount second = from.getName().compareTo(to.getName()) < 0 ? to : from;

        synchronized (first) {
            synchronized (second) {
                from.withdraw(amount);
                to.deposit(amount);
            }
        }
    }

    static class BankAccount {
        private final String name;
        private int balance;

        public BankAccount(String name, int balance) {
            this.name = name;
            this.balance = balance;
        }

        public void withdraw(int amount) {
            balance -= amount;
        }

        public void deposit(int amount) {
            balance += amount;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }
    }
}
