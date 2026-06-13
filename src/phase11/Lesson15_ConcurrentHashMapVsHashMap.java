package phase11;

import java.util.*;
import java.util.concurrent.*;

/**
 * LESSON 15: CONCURRENT HASHMAP VS HASHMAP
 *
 * Phase 12: JVM Internals & Backend Concepts
 *
 * This lesson covers:
 * 1. HashMap thread-unsafe behavior
 * 2. ConcurrentHashMap thread-safety
 * 3. Performance comparison
 * 4. Internal implementation
 * 5. Interview questions
 */

public class Lesson15_ConcurrentHashMapVsHashMap {
    public static void main(String[] args) {
        System.out.println("""
            === CONCURRENTHASHMAP VS HASHMAP ===
            
            1. HASHMAP THREAD-UNSAFE
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               HashMap is not thread-safe for concurrent access.
            
               WHY IT EXISTS:
               - Single-threaded performance
               - Simple implementation
            
               SIMPLE EXAMPLE:
                   Map<String, Integer> hashMap = new HashMap<>();
                   
                   // Multiple threads writing:
                   for (int i = 0; i < 100; i++) {
                       executor.submit(() -> 
                           hashMap.put("key" + i, i)
                       );
                   }
                   
                   // Result: size may be < 100
                   // Race conditions in put()
            
               REAL-WORLD BACKEND EXAMPLE:
                   A web application cache:
                   - Multiple requests update cache
                   - HashMap causes data corruption
                   - Users see stale/wrong data
            
               INTERVIEW QUESTION:
                   "Why is HashMap not thread-safe?
                   What happens during concurrent put()?"
            
               COMMON MISTAKES:
                   - Using HashMap in multi-threaded context
                   - Not understanding fail-fast
            
            ─────────────────────────────────────────────────────────────────────
            
            2. CONCURRENTHASHMAP THREAD-SAFETY
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               ConcurrentHashMap is thread-safe for concurrent access.
            
               WHY IT EXISTS:
               - Concurrent access without full locking
               - Better performance than synchronized
            
               SIMPLE EXAMPLE:
                   ConcurrentHashMap<String, Integer> concurrentMap = 
                       new ConcurrentHashMap<>();
                   
                   // Multiple threads writing:
                   for (int i = 0; i < 100; i++) {
                       executor.submit(() -> 
                           concurrentMap.put("key" + i, i)
                       );
                   }
                   
                   // Result: always 100
                   // Thread-safe without full locking
            
               REAL-WORLD BACKEND EXAMPLE:
                   A shared user cache:
                   private final ConcurrentHashMap<String, User> userCache = 
                       new ConcurrentHashMap<>();
                   
                   public User getOrCreate(String id) {
                       return userCache.computeIfAbsent(id, this::loadUser);
                   }
            
               INTERVIEW QUESTION:
                   "How does ConcurrentHashMap achieve thread-safety?
                   What is the difference from synchronized HashMap?"
            
               COMMON MISTAKES:
                   - Not understanding segment locking
                   - Using for single-threaded scenarios
            
            ─────────────────────────────────────────────────────────────────────
            
            3. PERFORMANCE COMPARISON
               ─────────────────────────────────────────────────────────────────────
               CONCEPT:
               ConcurrentHashMap typically outperforms synchronized HashMap.
            
               WHY IT EXISTS:
               - High-concurrency scenarios
               - Better scalability
            
               SIMPLE EXAMPLE:
                   // Synchronized HashMap:
                   Map<String, Integer> syncMap = 
                       Collections.synchronizedMap(new HashMap<>());
                   // Full map lock on every operation
                   
                   // ConcurrentHashMap:
                   ConcurrentHashMap<String, Integer> chm = 
                       new ConcurrentHashMap<>();
                   // Segment-level locking (Java 7)
                   // CAS + synchronized (Java 8+)
            
               REAL-WORLD BACKEND EXAMPLE:
                   A high-throughput API:
                   - 1000+ concurrent requests
                   - Shared cache for rate limiting
                   - ConcurrentHashMap scales better
            
               INTERVIEW QUESTION:
                   "When to use ConcurrentHashMap vs HashMap?
                   What about performance trade-offs?"
            
               COMMON MISTAKES:
                   - Not measuring actual performance
                   - Using for single-threaded
            
            ─────────────────────────────────────────────────────────────────────
            
            4. INTERNAL IMPLEMENTATION
               ─────────────────────────────────────────────────────────────────────
               JAVA 7 AND EARLIER:
                   - Segment-based locking
                   - 16 segments by default
                   - Each segment has its own lock
                   - Concurrent access to different segments
            
               JAVA 8+:
                   - Uses CAS (compare-and-swap)
                   - synchronized for bin updates
                   - Tree bins when size > threshold
                   - Better concurrent performance
            
               SIMPLE EXAMPLE:
                   // ConcurrentHashMap internal:
                   // - Hash table with array of bins
                   // - Each bin can be:
                   //   - LinkedList (small)
                   //   - TreeNode (large, Java 8+)
                   // - CAS for lock-free reads
            
               REAL-WORLD BACKEND EXAMPLE:
                   A cache with high read/write ratio:
                   - Reads use CAS (lock-free)
                   - Writes use synchronized
                   - Better throughput than full locking
            
               INTERVIEW QUESTION:
                   "How does ConcurrentHashMap work internally?
                   What changes in Java 8?"
            
               COMMON MISTAKES:
                   - Not understanding internal structure
                   - Not knowing about null restrictions
            
            ─────────────────────────────────────────────────────────────────────
            
            SUMMARY:
            ConcurrentHashMap is essential for:
            - Concurrent applications
            - Shared caches
            - High-throughput systems
            - Thread-safe data structures
            """);
    }
}
