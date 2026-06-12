package phase12.BEGINNER;

/**
 * INTERVIEW QUESTION: Difference between HashMap and HashTable?
 *
 * Difficulty: BEGINNER
 */

public class Lesson36_WhatIsTheDifferenceBetweenHashMapAndHashTable {
    public static void main(String[] args) {
        System.out.println("=== HASHMAP VS HASHTABLE ===\n");
        System.out.println("HashMap:");
        System.out.println("  - Not thread-safe");
        System.out.println("  - Allows null keys and values");
        System.out.println("  - Faster (no synchronization overhead)");
        System.out.println("  - Introduced in Java 1.2");
        System.out.println();
        System.out.println("HashTable:");
        System.out.println("  - Thread-safe (synchronized methods)");
        System.out.println("  - Does NOT allow null keys/values");
        System.out.println("  - Slower due to synchronization");
        System.out.println("  - Legacy class (Java 1.0)");
        System.out.println();
        System.out.println("Use HashMap for single-threaded, HashTable for legacy code");
        System.out.println("For thread-safe: Use ConcurrentHashMap instead");
    }
}
