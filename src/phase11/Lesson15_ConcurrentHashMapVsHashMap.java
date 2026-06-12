package phase11;

import java.util.*;
import java.util.concurrent.*;

/**
 * LESSON 15: CONCURRENT HASHMAP VS HASHMAP
 *
 * ============================================================
 * 1. CONCEPT IN SIMPLE TERMS
 * ============================================================
 * HashMap: Not thread-safe, single-threaded use
 * ConcurrentHashMap: Thread-safe, allows concurrent reads/writes
 * Like a single-lane road vs multi-lane highway.
 *
 * ============================================================
 * 2. WHY IT EXISTS
 * ============================================================
 * - HashMap fails in multi-threaded environments
 * - ConcurrentHashMap provides thread safety without full locking
 * - Better performance than synchronized HashMap
 */

public class Lesson15_ConcurrentHashMapVsHashMap {

    public static void main(String[] args) {
        System.out.println("=== CONCURRENTHASHMAP VS HASHMAP ===\n");

        // ============================================================
        // EXAMPLE 1: HashMap thread-unsafe
        // ============================================================
        System.out.println("--- Example 1: HashMap Not Thread-Safe ---\n");

        Map<String, Integer> hashMap = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Multiple threads writing
        for (int i = 0; i < 100; i++) {
            final int key = i;
            executor.submit(() -> hashMap.put("key" + key, key));
        }

        executor.shutdown();
        try { executor.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) {}

        System.out.println("HashMap size (may be wrong): " + hashMap.size());
        System.out.println("Expected: 100");
        System.out.println("HashMap is NOT thread-safe!");
        System.out.println();

        // ============================================================
        // EXAMPLE 2: ConcurrentHashMap thread-safe
        // ============================================================
        System.out.println("--- Example 2: ConcurrentHashMap Thread-Safe ---\n");

        ConcurrentHashMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 100; i++) {
            final int key = i;
            executor.submit(() -> concurrentMap.put("key" + key, key));
        }

        executor.shutdown();
        try { executor.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) {}

        System.out.println("ConcurrentHashMap size: " + concurrentMap.size());
        System.out.println("Expected: 100");
        System.out.println("ConcurrentHashMap IS thread-safe!");
        System.out.println();

        // ============================================================
        // EXAMPLE 3: Performance comparison
        // ============================================================
        System.out.println("--- Example 3: Performance ---\n");

        int threads = 10;
        int operations = 10000;

        // HashMap with synchronization
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

        long start = System.nanoTime();
        executor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < operations; i++) {
            final int key = i;
            executor.submit(() -> syncMap.put("key" + key, key));
        }
        executor.shutdown();
        try { executor.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) {}
        long syncTime = System.nanoTime() - start;

        // ConcurrentHashMap
        ConcurrentHashMap<String, Integer> chm = new ConcurrentHashMap<>();

        start = System.nanoTime();
        executor = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < operations; i++) {
            final int key = i;
            executor.submit(() -> chm.put("key" + key, key));
        }
        executor.shutdown();
        try { executor.awaitTermination(1, TimeUnit.SECONDS); } catch (InterruptedException e) {}
        long chmTime = System.nanoTime() - start;

        System.out.println("Synchronized HashMap: " + syncTime / 1_000_000 + " ms");
        System.out.println("ConcurrentHashMap: " + chmTime / 1_000_000 + " ms");
        System.out.println("ConcurrentHashMap is typically faster!");
        System.out.println();
    }

    // ============================================================
    // CONCURRENTHASHMAP VS HASHMAP
    // ============================================================
    /*
     * Key Differences:
     *
     * 1. Thread Safety:
     *    - HashMap: NOT thread-safe
     *    - ConcurrentHashMap: Thread-safe
     *
     * 2. Locking Strategy:
     *    - HashMap: No locking
     *    - ConcurrentHashMap: Segment-level locking (Java 7)
     *      or CAS + synchronized (Java 8+)
     *
     * 3. Null Values:
     *    - HashMap: Allows null keys and values
     *    - ConcurrentHashMap: Does NOT allow null keys/values
     *
     * 4. Iteration:
     *    - HashMap: Fail-fast (throws ConcurrentModificationException)
     *    - ConcurrentHashMap: Weakly consistent (reflects state at some point)
     *
     * 5. Performance:
     *    - HashMap: Fastest (single-threaded)
     *    - ConcurrentHashMap: Better than synchronized HashMap
     *
     * Java 8+ Improvements:
     * - Uses CAS operations instead of locks
     * - Tree bins when size > threshold
     * - Better concurrent performance
     */
}
