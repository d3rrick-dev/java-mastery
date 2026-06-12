package phase12.ADVANCED;

/**
 * INTERVIEW QUESTION: Difference between HashMap and HashTable?
 *
 * Difficulty: ADVANCED
 */

public class Lesson07_WhatIsTheDifferenceBetweenHashMapAndHashTable {
    public static void main(String[] args) {
        System.out.println("=== HASHMAP VS HASHTABLE ===\n");
        System.out.println("HashMap:");
        System.out.println("  - Not synchronized (not thread-safe)");
        System.out.println("  - Allows null keys and values");
        System.out.println("  - Faster (no synchronization overhead)");
        System.out.println("  - Introduced in Java 1.2");
        System.out.println("  - Uses Iterator (fail-fast)");
        System.out.println();
        System.out.println("HashTable:");
        System.out.println("  - Synchronized (thread-safe)");
        System.out.println("  - Does NOT allow null keys/values");
        System.out.println("  - Slower (synchronization overhead)");
        System.out.println("  - Legacy class (Java 1.0)");
        System.out.println("  - Uses Enumerator (not fail-fast)");
        System.out.println();
        System.out.println("Modern Alternative:");
        System.out.println("  - Use ConcurrentHashMap for thread-safe maps");
        System.out.println("  - Better performance than HashTable");
    }
}
