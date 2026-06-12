package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between HashMap and ConcurrentHashMap?
 *
 * Difficulty: ADVANCED
 */

public class Lesson08_WhatIsTheDifferenceBetweenHashMapAndConcurrentHashMap {
    public static void main(String[] args) {
        System.out.println("=== HASHMAP VS CONCURRENTHASHMAP ===\n");
        System.out.println("HashMap:");
        System.out.println("  - Not thread-safe");
        System.out.println("  - Allows null keys and values");
        System.out.println("  - Single lock for entire map (if synchronized externally)");
        System.out.println("  - Iterator is fail-fast");
        System.out.println();
        System.out.println("ConcurrentHashMap:");
        System.out.println("  - Thread-safe (designed for concurrency)");
        System.out.println("  - Does NOT allow null keys/values");
        System.out.println("  - Lock striping (segment-level locking in Java 7, CAS in Java 8+)");
        System.out.println("  - Iterator is weakly consistent (doesn't throw ConcurrentModificationException)");
        System.out.println("  - Better performance in multi-threaded environments");
        System.out.println();
        System.out.println("How ConcurrentHashMap achieves thread safety:");
        System.out.println("  - Java 7: Segments (16 by default), each with its own lock");
        System.out.println("  - Java 8+: Removed segments, uses synchronized blocks + CAS");
        System.out.println("  - Read operations: no locking");
        System.out.println("  - Write operations: lock only affected bucket");
    }
}
