package phase7;

/**
 * LESSON 15: LIVELOCKS
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * A livelock is when threads are NOT blocked, but are constantly
 * responding to each other and making no progress. Like two people
 * in a hallway both stepping aside for each other - they keep
 * moving but never pass.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - Threads are too polite (keep yielding to each other)
 * - No thread is blocked, but no work gets done
 * - Often caused by overly aggressive retry logic
 * - Similar to deadlock but threads are active
 *
 * ============================================================
 * 3. REAL-WORLD EXAMPLE
 * ============================================================
 * Two people in narrow hallway:
 * - Person A steps right
 * - Person B steps left (to let A pass)
 * - Now B is blocking A, so A steps left
 * - Now A is blocking B, so B steps right
 * - They keep dancing forever!
 *
 * ============================================================
 * 4. BACKEND ENGINEERING EXAMPLE
 * ============================================================
 * Two services retrying failed requests:
 * - Service A fails, retries
 * - Service B fails, retries
 * - Both keep retrying, overwhelming each other
 * - No progress, high CPU usage
 *
 * ============================================================
 * 5. CODING INTERVIEW EXAMPLE
 * ============================================================
 * "What is a livelock?"
 * Answer: Threads are active but making no progress,
 *         constantly responding to each other
 *
 * ============================================================
 * 6. COMMON MISTAKES
 * ============================================================
 * - Aggressive retry without backoff
 * - Too polite lock acquisition (always yielding)
 * - No random delay in retry logic
 * - Not detecting livelock (harder than deadlock)
 *
 * ============================================================
 * 7. PERFORMANCE IMPLICATIONS
 * ============================================================
 * - 100% CPU usage with no progress
 * - Wastes resources
 * - Hard to detect (threads not blocked)
 * - Can last indefinitely
 *
 * ============================================================
 * LIVELOCK vs DEADLOCK
 * ============================================================
 *
 *   DEADLOCK                    LIVELOCK
 *   +----------------+          +----------------+
 *   | Threads BLOCKED|          | Threads ACTIVE |
 *   | No CPU usage   |          | 100% CPU usage |
 *   | Easy to detect |          | Hard to detect |
 *   | State: BLOCKED |          | State: RUNNABLE|
 *   +----------------+          +----------------+
 *   | Both stuck     |          | Both busy      |
 *   +----------------+          +----------------+
 */

public class Lesson15_Livelocks {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== LIVELOCKS ===\n");

        // ============================================================
        // EXAMPLE 1: Polite people (classic livelock)
        // ============================================================
        System.out.println("--- Example 1: Polite People Livelock ---\n");

        PolitePerson husband = new PolitePerson("Husband", true);
        PolitePerson wife = new PolitePerson("Wife", true);

        Thread t1 = new Thread(() -> husband.walkTo(wife));
        Thread t2 = new Thread(() -> wife.walkTo(husband));

        t1.start();
        t2.start();

        // Let them livelock for a bit
        Thread.sleep(2000);

        System.out.println("Both still running (livelock detected)!");
        System.out.println("Husband steps: " + husband.getSteps());
        System.out.println("Wife steps: " + wife.getSteps() + "\n");

        // Stop them
        husband.stop();
        wife.stop();
        t1.interrupt();
        t2.interrupt();
        t1.join();
        t2.join();

        // ============================================================
        // EXAMPLE 2: Fixed with random backoff
        // ============================================================
        System.out.println("--- Example 2: Fixed with Random Backoff ---\n");

        SmartPerson smart1 = new SmartPerson("Smart-1");
        SmartPerson smart2 = new SmartPerson("Smart-2");

        Thread t3 = new Thread(() -> smart1.walkTo(smart2));
        Thread t4 = new Thread(() -> smart2.walkTo(smart1));

        t3.start();
        t4.start();

        Thread.sleep(2000);

        System.out.println("Smart-1 steps: " + smart1.getSteps());
        System.out.println("Smart-2 steps: " + smart2.getSteps());
        System.out.println("(One should have passed!)\n");

        smart1.stop();
        smart2.stop();
        t3.interrupt();
        t4.interrupt();
        t3.join();
        t4.join();

        // ============================================================
        // EXAMPLE 3: Retry livelock
        // ============================================================
        System.out.println("--- Example 3: Retry Livelock ---\n");

        ResourceLock lock = new ResourceLock();

        Thread retryThread1 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (lock.tryLock()) {
                    try {
                        System.out.println("Thread 1 got lock, working...");
                        Thread.sleep(100);
                        break;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("Thread 1: Lock busy, retrying immediately...");
                    // No delay = livelock!
                }
            }
        });

        Thread retryThread2 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (lock.tryLock()) {
                    try {
                        System.out.println("Thread 2 got lock, working...");
                        Thread.sleep(100);
                        break;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock.unlock();
                    }
                } else {
                    System.out.println("Thread 2: Lock busy, retrying immediately...");
                    // No delay = livelock!
                }
            }
        });

        retryThread1.start();
        retryThread2.start();

        Thread.sleep(1000);
        System.out.println("Retry livelock in progress...\n");

        retryThread1.interrupt();
        retryThread2.interrupt();
        retryThread1.join();
        retryThread2.join();

        // ============================================================
        // EXAMPLE 4: Fixed retry with backoff
        // ============================================================
        System.out.println("--- Example 4: Fixed Retry with Backoff ---\n");

        ResourceLock lock2 = new ResourceLock();

        Thread backoffThread1 = new Thread(() -> {
            int attempts = 0;
            while (!Thread.currentThread().isInterrupted() && attempts < 10) {
                if (lock2.tryLock()) {
                    try {
                        System.out.println("Backoff Thread 1 got lock!");
                        Thread.sleep(100);
                        break;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    attempts++;
                    try {
                        // Exponential backoff
                        Thread.sleep(10 * attempts);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        Thread backoffThread2 = new Thread(() -> {
            int attempts = 0;
            while (!Thread.currentThread().isInterrupted() && attempts < 10) {
                if (lock2.tryLock()) {
                    try {
                        System.out.println("Backoff Thread 2 got lock!");
                        Thread.sleep(100);
                        break;
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        lock2.unlock();
                    }
                } else {
                    attempts++;
                    try {
                        Thread.sleep(10 * attempts);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });

        backoffThread1.start();
        backoffThread2.start();
        backoffThread1.join();
        backoffThread2.join();
        System.out.println("Backoff threads completed!\n");

        // ============================================================
        // EXAMPLE 5: Livelock detection
        // ============================================================
        System.out.println("--- Example 5: Livelock Detection ---\n");

        System.out.println("How to detect livelock:");
        System.out.println("1. Monitor thread states (all RUNNABLE)");
        System.out.println("2. Track progress metrics (no progress over time)");
        System.out.println("3. Monitor CPU usage (high but no work done)");
        System.out.println("4. Set timeout for operations");
        System.out.println("5. Use health checks with timeouts\n");

        // ============================================================
        // EXAMPLE 6: Prevention strategies
        // ============================================================
        System.out.println("--- Example 6: Prevention Strategies ---\n");

        System.out.println("1. Add Random Delay:");
        System.out.println("   Thread.sleep(random) before retry\n");

        System.out.println("2. Exponential Backoff:");
        System.out.println("   Wait longer with each retry\n");

        System.out.println("3. Maximum Retry Limit:");
        System.out.println("   Give up after N attempts\n");

        System.out.println("4. Priority-based Locking:");
        System.out.println("   Lower priority thread yields\n");

        System.out.println("5. Circuit Breaker:");
        System.out.println("   Stop retrying after failures\n");
    }

    // ============================================================
    // EXAMPLE CLASSES
    // ============================================================

    static class PolitePerson {
        private final String name;
        private final boolean polite;
        private volatile boolean active = true;
        private int steps = 0;

        public PolitePerson(String name, boolean polite) {
            this.name = name;
            this.polite = polite;
        }

        public void walkTo(PolitePerson other) {
            while (active) {
                // Step forward
                steps++;

                // If other is in the way, step aside
                if (other.isInTheWay()) {
                    if (polite) {
                        stepAside();
                    }
                }

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        private boolean isInTheWay() {
            return steps % 2 == 0;
        }

        private void stepAside() {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void stop() {
            active = false;
        }

        public int getSteps() {
            return steps;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    static class SmartPerson {
        private final String name;
        private volatile boolean active = true;
        private int steps = 0;
        private long lastStepTime = 0;

        public SmartPerson(String name) {
            this.name = name;
        }

        public void walkTo(SmartPerson other) {
            while (active) {
                long now = System.currentTimeMillis();

                // If we've been stepping for too long, wait
                if (now - lastStepTime < 50) {
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    continue;
                }

                steps++;
                lastStepTime = now;

                // Random chance to just go forward
                if (Math.random() > 0.3) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }

        public void stop() {
            active = false;
        }

        public int getSteps() {
            return steps;
        }
    }

    static class ResourceLock {
        private boolean locked = false;
        private Thread owner = null;

        public synchronized boolean tryLock() {
            if (!locked) {
                locked = true;
                owner = Thread.currentThread();
                return true;
            }
            return false;
        }

        public synchronized void unlock() {
            if (owner == Thread.currentThread()) {
                locked = false;
                owner = null;
                notifyAll();
            }
        }
    }
}
