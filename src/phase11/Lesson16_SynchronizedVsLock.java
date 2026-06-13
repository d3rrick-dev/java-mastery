package phase11;

import java.util.concurrent.locks.*;

/**
 * LESSON 16: SYNCHRONIZED VS LOCK
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. synchronized keyword
 * 2. Lock interface
 * 3. tryLock()
 * 4. Fair vs Non-fair locks
 * 5. Interview questions
 */

public class Lesson16_SynchronizedVsLock {
    public static void main(String[] args) {
        System.out.println("""
            === SYNCHRONIZED VS LOCK ===
            
            1. SYNCHRONIZED KEYWORD
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Built-in Java keyword for mutual exclusion.
            
               WHY IT EXISTS:
               - Simple locking
               - Automatic lock management
            
               SIMPLE EXAMPLE:
                   // Method-level:
                   public synchronized void method() {
                       // Critical section
                       // Lock released automatically
                   }
                   
                   // Block-level:
                   synchronized (lock) {
                       // Critical section
                   }
                   
                   // Features:
                   // - Implicit lock acquisition/release
                   // - Lock released on exception
                   // - Non-fair (no ordering)
            
               REAL-WORLD BACKEND EXAMPLE:
                   A service method:
                   public synchronized void processOrder(Order order) {
                       // Only one order processed at a time
                       // Simple, automatic cleanup
                   }
            
               INTERVIEW QUESTION:
                   "How does synchronized work internally?
                   What is the monitor?"
            
               COMMON MISTAKES:
                   - Not understanding scope
                   - Deadlock from multiple locks
            
            ─────────────────────────────────────────────────────────────────────
            
            2. LOCK INTERFACE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Explicit lock with more control and features.
            
               WHY IT EXISTS:
               - More control over locking
               - Advanced features
            
               SIMPLE EXAMPLE:
                   Lock lock = new ReentrantLock();
                   
                   lock.lock();
                   try {
                       // Critical section
                   } finally {
                       lock.unlock();  // Must release manually!
                   }
                   
                   // Features:
                   // - tryLock(): Non-blocking attempt
                   // - lockInterruptibly(): Interruptible
                   // - Fairness policy
                   // - Multiple Condition objects
            
               REAL-WORLD BACKEND EXAMPLE:
                   A timeout-based cache:
                   if (lock.tryLock(5, TimeUnit.SECONDS)) {
                       try {
                           return cache.get(key);
                       } finally {
                           lock.unlock();
                       }
                   } else {
                       throw new TimeoutException("Cache busy");
                   }
            
               INTERVIEW QUESTION:
                   "What are the advantages of Lock over synchronized?
                   When would you use each?"
            
               COMMON MISTAKES:
                   - Forgetting unlock()
                   - Not using try-finally
            
            ─────────────────────────────────────────────────────────────────────
            
            3. TRYLOCK()
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Non-blocking attempt to acquire lock.
            
               WHY IT EXISTS:
               - Avoid indefinite blocking
               - Timeout handling
            
               SIMPLE EXAMPLE:
                   Lock lock = new ReentrantLock();
                   
                   // Non-blocking:
                   boolean acquired = lock.tryLock();
                   if (acquired) {
                       try {
                           // Do work
                       } finally {
                           lock.unlock();
                       }
                   } else {
                       // Do alternative
                   }
                   
                   // With timeout:
                   boolean acquired = lock.tryLock(5, TimeUnit.SECONDS);
            
               REAL-WORLD BACKEND EXAMPLE:
                   A circuit breaker:
                   if (lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                       try {
                           if (state == OPEN) {
                               return fallback();
                           }
                           // Process
                       } finally {
                           lock.unlock();
                       }
                   }
            
               INTERVIEW QUESTION:
                   "What is tryLock()?
                   How to implement timeout-based locking?"
            
               COMMON MISTAKES:
                   - Not handling false return
                   - Not using timeout
            
            ─────────────────────────────────────────────────────────────────────
            
            4. FAIR VS NON-FAIR LOCKS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Fair locks provide FIFO ordering, non-fair don't.
            
               WHY IT EXISTS:
               - Prevent thread starvation
               - Predictable ordering
            
               SIMPLE EXAMPLE:
                   // Fair lock:
                   Lock fairLock = new ReentrantLock(true);
                   // FIFO ordering
                   // Higher overhead
                   
                   // Non-fair lock (default):
                   Lock nonFairLock = new ReentrantLock(false);
                   // No ordering
                   // Lower overhead
                   // Higher throughput
            
               REAL-WORLD BACKEND EXAMPLE:
                   A request queue:
                   - Fair lock ensures FIFO processing
                   - Prevents request starvation
                   - Trade-off: lower throughput
            
               INTERVIEW QUESTION:
                   "What is the difference between fair and non-fair locks?
                   When to use each?"
            
               COMMON MISTAKES:
                   - Always using fair (performance hit)
                   - Not understanding overhead
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Locking mechanisms are essential for:
            - Thread safety
            - Resource protection
            - Performance optimization
            - Deadlock prevention
            """);
    }
}
