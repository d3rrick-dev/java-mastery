package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between ConcurrentHashMap and Collections.synchronizedMap?
 *
 * Difficulty: ADVANCED
 */

public class Lesson25_WhatIsTheDifferenceBetweenConcurrentHashMapAndSynchronizedMap {
    public static void main(String[] args) {
        System.out.println("=== CONCURRENTHASHMAP VS SYNCHRONIZEDMAP ===\n");
        System.out.println("ConcurrentHashMap:");
        System.out.println("  - Designed for concurrent access");
        System.out.println("  - Lock striping (segment-level or bucket-level)");
        System.out.println("  - Multiple threads can read simultaneously");
        System.out.println("  - Multiple threads can write to different segments");
        System.out.println("  - Better performance in multi-threaded environments");
        System.out.println("  - Iterator is weakly consistent");
        System.out.println();
        System.out.println("Collections.synchronizedMap():");
        System.out.println("  - Wraps any Map with synchronized methods");
        System.out.println("  - Single lock for entire map");
        System.out.println("  - Only one thread can access at a time (read or write)");
        System.out.println("  - Poor performance under high concurrency");
        System.out.println("  - Iterator is fail-fast (must manually synchronize)");
        System.out.println();
        System.out.println("Performance Comparison:");
        System.out.println("  - ConcurrentHashMap: O(1) for reads, O(1) for writes (different buckets)");
        System.out.println("  - SynchronizedMap: O(1) but with global lock contention");
        System.out.println();
        System.out.println("Interview Tip:");
        System.out.println("  - Always prefer ConcurrentHashMap for thread-safe maps");
        System.out.println("  - synchronizedMap is legacy approach");
    }
}
