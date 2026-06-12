package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between HashMap and ConcurrentHashMap?
 *
 * Difficulty: BEGINNER
 */

public class Lesson37_WhatIsTheDifferenceBetweenHashMapAndConcurrentHashMap {
    public static void main(String[] args) {
        System.out.println("=== HASHMAP VS CONCURRENTHASHMAP ===\n");
        System.out.println("HashMap:");
        System.out.println("  - Not thread-safe");
        System.out.println("  - Allows null keys/values");
        System.out.println("  - Single lock for entire map (legacy)");
        System.out.println();
        System.out.println("ConcurrentHashMap:");
        System.out.println("  - Thread-safe");
        System.out.println("  - Does NOT allow null keys/values");
        System.out.println("  - Lock striping (segment-level locking)");
        System.out.println("  - Better concurrency (multiple readers, limited writers)");
        System.out.println();
        System.out.println("Use ConcurrentHashMap for multi-threaded environments");
    }
}
