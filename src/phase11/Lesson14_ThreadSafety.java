package phase11;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LESSON 14: THREAD SAFETY
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. Thread-unsafe code
 * 2. Synchronized methods
 * 3. Atomic operations
 * 4. Thread-safe collections
 * 5. Interview questions
 */

public class Lesson14_ThreadSafety {
    public static void main(String[] args) {
        System.out.println("""
            === THREAD SAFETY ===
            
            1. THREAD-UNSAFE CODE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Code that produces incorrect results under concurrent access.
            
               WHY IT EXISTS:
               - Race conditions
               - Non-atomic operations
            
               SIMPLE EXAMPLE:
                   class UnsafeCounter {
                       private int count = 0;
                       
                       public void increment() {
                           count++;  // Not atomic! Read-modify-write race
                       }
                   }
                   
                   // Multiple threads calling increment():
                   // Thread 1: read count=0, write count=1
                   // Thread 2: read count=0, write count=1
                   // Result: 1 instead of 2
            
               REAL-WORLD BACKEND EXAMPLE:
                   A request counter:
                   - Multiple requests increment counter
                   - Race condition causes wrong count
                   - Monitoring shows incorrect metrics
            
               INTERVIEW QUESTION:
                   "What is a race condition?
                   How to identify thread-unsafe code?"
            
               COMMON MISTAKES:
                   - Assuming operations are atomic
                   - Not testing under load
            
            ─────────────────────────────────────────────────────────────────────
            
            2. SYNCHRONIZED METHODS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Mutual exclusion using synchronized keyword.
            
               WHY IT EXISTS:
               - Prevent concurrent access
               - Ensure atomicity
            
               SIMPLE EXAMPLE:
                   class SafeCounter {
                       private int count = 0;
                       
                       public synchronized void increment() {
                           count++;  // Only one thread at a time
                       }
                       
                       public synchronized int getCount() {
                           return count;
                       }
                   }
                   
                   // Intrinsic lock on 'this'
                   // Blocks other synchronized methods
            
               REAL-WORLD BACKEND EXAMPLE:
                   A cache with synchronized access:
                   public synchronized User get(String key) {
                       return cache.get(key);
                   }
                   
                   public synchronized void put(String key, User user) {
                       cache.put(key, user);
                   }
            
               INTERVIEW QUESTION:
                   "How does synchronized work internally?
                   What is the monitor?"
            
               COMMON MISTAKES:
                   - Not synchronizing all access
                   - Deadlock from multiple locks
            
            ─────────────────────────────────────────────────────────────────────
            
            3. ATOMIC OPERATIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Lock-free thread-safe operations using CPU instructions.
            
               WHY IT EXISTS:
               - Better performance
               - No blocking
               - Lock-free algorithms
            
               SIMPLE EXAMPLE:
                   class AtomicCounter {
                       private final AtomicInteger count = new AtomicInteger(0);
                       
                       public void increment() {
                           count.incrementAndGet();  // Atomic CPU instruction
                       }
                       
                       public int getCount() {
                           return count.get();
                       }
                   }
                   
                   // Uses compare-and-swap (CAS)
                   // No blocking, better for high contention
            
               REAL-WORLD BACKEND EXAMPLE:
                   A rate limiter:
                   private final AtomicInteger requestCount = new AtomicInteger(0);
                   
                   public boolean allowRequest() {
                       return requestCount.incrementAndGet() <= maxRequests;
                   }
            
               INTERVIEW QUESTION:
                   "How do atomic operations work?
                   What is compare-and-swap (CAS)?"
            
               COMMON MISTAKES:
                   - Not handling ABA problem
                   - Using for complex operations
            
            ─────────────────────────────────────────────────────────────────────
            
            4. THREAD-SAFE COLLECTIONS
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               Collections designed for concurrent access.
            
               WHY IT EXISTS:
               - Safe concurrent data structures
               - Better performance than synchronization
            
               SIMPLE EXAMPLE:
                   // Not thread-safe:
                   Map<String, User> map = new HashMap<>();
                   
                   // Thread-safe:
                   Map<String, User> concurrentMap = new ConcurrentHashMap<>();
                   concurrentMap.put("key", user);  // Safe
                   
                   // Copy-on-write:
                   List<String> cowList = new CopyOnWriteArrayList<>();
                   cowList.add("item");  // Creates new copy
            
               REAL-WORLD BACKEND EXAMPLE:
                   A shared cache:
                   private final ConcurrentHashMap<String, User> userCache = 
                       new ConcurrentHashMap<>();
                   
                   public User getOrCreate(String id) {
                       return userCache.computeIfAbsent(id, this::loadUser);
                   }
            
               INTERVIEW QUESTION:
                   "What is ConcurrentHashMap?
                   How does it achieve thread-safety without locking?"
            
               COMMON MISTAKES:
                   - Not using concurrent collections
                   - Not understanding segment locking
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            Thread safety is critical for:
            - Concurrent applications
            - Web servers
            - Shared state
            - Performance optimization
            """);
    }
}
